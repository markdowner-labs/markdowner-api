#!/bin/bash

PROFILE="$HOME/.profile"

declare -A vars=(
  [POSTGRES_IPV4]="127.0.0.1"
  [POSTGRES_PORT]="5432"
  [POSTGRES_DB]="markdowner-db"
  [POSTGRES_USER]="postgres"
  [POSTGRES_PASSWORD]="123456"
  [SPRINGBOOT_DATASOURCE_USERNAME]='$POSTGRES_USER'
  [SPRINGBOOT_DATASOURCE_PASSWORD]='$POSTGRES_PASSWORD'
  [SPRINGBOOT_DATASOURCE_URL]='jdbc:postgresql://$POSTGRES_IPV4:$POSTGRES_PORT/$POSTGRES_DB'
  [SPRINGBOOT_JPA_HIBERNATE_DDL_AUTO]="create"
  [REDIS_IPV4]="127.0.0.1"
  [REDIS_PORT]="6379"
  [REDIS_PASSWORD]="123456"
  [SPRINGBOOT_DATA_REDIS_HOST]='$REDIS_IPV4'
  [SPRINGBOOT_DATA_REDIS_PORT]='$REDIS_PORT'
  [SPRINGBOOT_DATA_REDIS_PASSWORD]='$REDIS_PASSWORD'
  [SPRINGBOOT_CACHE_REDIS_TIME_TO_LIVE]="30000"
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
