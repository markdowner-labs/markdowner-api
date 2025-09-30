#!/bin/bash

PROFILE="$HOME/.profile"

declare -A vars=(
  [SPRINGBOOT_DATASOURCE_USERNAME]='postgres'
  [SPRINGBOOT_DATASOURCE_PASSWORD]='123456'
  [SPRINGBOOT_DATASOURCE_URL]='jdbc:postgresql://127.0.0.1:5432/markdowner-db'
  [SPRINGBOOT_JPA_HIBERNATE_DDL_AUTO]='create'
  [SPRINGBOOT_DATA_REDIS_HOST]='127.0.0.1'
  [SPRINGBOOT_DATA_REDIS_PORT]='6379'
  [SPRINGBOOT_DATA_REDIS_PASSWORD]='123456'
  [SPRINGBOOT_CACHE_REDIS_TIME_TO_LIVE]='30000'
)

for key in "${!vars[@]}"; do
  value="${vars[$key]}"
  if grep -qE "^export $key=" "$PROFILE"; then
    current_val=$(grep -E "^export $key=" "$PROFILE" | sed -E "s/^export $key=//")
    if [[ "$current_val" != "$value" ]]; then
      sed -i -E "s|^export $key=.*|export $key=$value|" "$PROFILE"
      echo "Updated: $key"
    fi
  else
    echo "export $key=$value" >> "$PROFILE"
    echo "Created: $key"
  fi
done
