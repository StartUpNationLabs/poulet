#!/bin/env bash

# List of environment variables to include
include_vars=("ALERT_MANAGEMENT_BASE_URL" "PATIENT_MANAGEMENT_BASE_URL")

# Convert specified environment variables to JSON format
{
  echo "{"
  for var in "${include_vars[@]}"; do
    if [ -n "${!var}" ]; then
      echo "\"$var\":\"${!var}\","
    fi
  done
  echo "}" | sed 's/,}/}/'
} > /usr/share/nginx/html/env

# Start Nginx
nginx -g "daemon off;"