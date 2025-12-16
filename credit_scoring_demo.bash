#!/usr/bin/env bash

set -euo pipefail

# =========================
# Colors (portable)
# =========================
RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
YELLOW=$(tput setaf 3)
BLUE=$(tput setaf 4)
CYAN=$(tput setaf 6)
RESET=$(tput sgr0)

# =========================
# Configuration
# =========================
BASE_URL="http://localhost:8080"
CUSTOMER_ID=1

HEADERS=(
  -H "Content-Type: application/json"
  -H "Accept: application/json"
)

# =========================
# Helper: API caller
# =========================
call_api() {
  local method=$1
  local endpoint=$2
  local data=${3:-}

  echo -e "\n${CYAN}➡️  ${method} ${endpoint}${RESET}"

  if [[ -n "$data" ]]; then
    response=$(curl -s -w "\n%{http_code}" \
      -X "$method" "${BASE_URL}${endpoint}" \
      "${HEADERS[@]}" \
      -d "$data")
  else
    response=$(curl -s -w "\n%{http_code}" \
      -X "$method" "${BASE_URL}${endpoint}" \
      "${HEADERS[@]}")
  fi

  body=$(echo "$response" | sed '$d')
  status=$(echo "$response" | tail -n1)

  if [[ "$status" -ge 200 && "$status" -lt 300 ]]; then
    echo -e "${GREEN}✅ Success ($status)${RESET}"
    command -v jq >/dev/null 2>&1 && echo "$body" | jq || echo "$body"
  else
    echo -e "${RED}❌ Error ($status)${RESET}"
    echo "$body"
  fi
}

pause() {
  echo
  read -rp "Press Enter to continue..."
}

# =========================
# Startup menu
# =========================
startup_menu() {
  clear
  echo -e "${CYAN}=====================================${RESET}"
  echo -e "${CYAN} Customer API Runner${RESET}"
  echo -e "${CYAN}=====================================${RESET}"
  echo
  echo " 1) Interactive mode"
  echo " 2) Run ALL commands (defaults)"
  echo " 0) Exit"
  echo
}

# =========================
# Main menu
# =========================
show_menu() {
  clear
  echo -e "${CYAN}=====================================${RESET}"
  echo -e "${CYAN} Customer API Menu (ID: ${CUSTOMER_ID})${RESET}"
  echo -e "${CYAN}=====================================${RESET}"
  echo
  echo " 1) Create bank account"
  echo " 2) Create loan"
  echo " 3) Set registered status"
  echo " 4) Set convictions status"
  echo " 5) Get score"
  echo " 6) Delete customer data"
  echo " 0) Back"
  echo
}

# =========================
# INTERACTIVE actions
# =========================
create_bank_account() {
  read -rp "currentBalance [1000]: " balance
  read -rp "overdraftLimit [500]: " overdraft
  read -rp "numberOfTimesOverdrawn [0]: " times

  balance=${balance:-1000}
  overdraft=${overdraft:-500}
  times=${times:-0}

  call_api "POST" "/financial/${CUSTOMER_ID}/bankaccount" "{
    \"currentBalance\": ${balance},
    \"overdraftLimit\": ${overdraft},
    \"numberOfTimesOverdrawn\": ${times}
  }"
}

create_loan() {
  read -rp "Account type [LOAN]: " type
  read -rp "Loan amount [10000]: " amount
  read -rp "Number of missed payments [0]: " missed

  type=$(echo "${type:-LOAN}" | tr '[:lower:]' '[:upper:]')
  amount=${amount:-10000}
  missed=${missed:-0}

  call_api "POST" "/financial/${CUSTOMER_ID}/loan" "{
    \"accountType\": \"${type}\",
    \"amount\": ${amount},
    \"numberOfMissedPayments\": ${missed}
  }"
}

set_registered() {
  read -rp "Registered to vote? (true/false) [true]: " value
  value=${value:-true}
  lowered=$(echo "$value" | tr '[:upper:]' '[:lower:]')
  call_api "PUT" "/legal/${CUSTOMER_ID}/registered?vote=${lowered}"
}

set_convictions() {
  read -rp "Convicted? (true/false) [false]: " value
  value=${value:-false}
  lowered=$(echo "$value" | tr '[:upper:]' '[:lower:]')
  call_api "PUT" "/legal/${CUSTOMER_ID}/convictions?convicted=${lowered}"
}

delete_data() {
  read -rp "Type YES to confirm delete: " confirm
  [[ "$confirm" == "YES" ]] && call_api "DELETE" "/data/${CUSTOMER_ID}" \
    || echo -e "${YELLOW}Delete cancelled${RESET}"
}

# =========================
# NON-INTERACTIVE actions
# =========================
create_bank_account_default() {
  call_api "POST" "/financial/${CUSTOMER_ID}/bankaccount" '{
    "currentBalance": 1000,
    "overdraftLimit": 500,
    "numberOfTimesOverdrawn": 0
  }'
}

create_loan_default() {
  call_api "POST" "/financial/${CUSTOMER_ID}/loan" '{
    "accountType": "LOAN",
    "amount": 10000,
    "numberOfMissedPayments": 0
  }'
}

set_registered_default() {
  call_api "PUT" "/legal/${CUSTOMER_ID}/registered?vote=true"
}

set_convictions_default() {
  call_api "PUT" "/legal/${CUSTOMER_ID}/convictions?convicted=false"
}

delete_data_default() {
  call_api "DELETE" "/data/${CUSTOMER_ID}"
}

get_score() {
  call_api "GET" "/score/${CUSTOMER_ID}"
}

run_all_defaults() {
  create_bank_account_default
  create_loan_default
  set_registered_default
  set_convictions_default
  get_score
  delete_data_default
}

# =========================
# Entry point
# =========================
while true; do
  startup_menu
  read -rp "Choose mode [0–2]: " mode

  case "$mode" in
    1)
      while true; do
        show_menu
        read -rp "Choose option [0–6]: " choice
        case "$choice" in
          1) create_bank_account; pause ;;
          2) create_loan; pause ;;
          3) set_registered; pause ;;
          4) set_convictions; pause ;;
          5) get_score; pause ;;
          6) delete_data; pause ;;
          0) break ;;
          *) echo -e "${RED}Invalid option${RESET}"; pause ;;
        esac
      done
      ;;
    2)
      run_all_defaults
      pause
      ;;
    0)
      echo -e "${CYAN}👋 Goodbye${RESET}"
      exit 0
      ;;
    *)
      echo -e "${RED}Invalid selection${RESET}"
      pause
      ;;
  esac
done
