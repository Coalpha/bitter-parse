#!/usr/bin/env bash

echo "deleting these files:"
find . -name "*.class" -type f
if [[ $1 == yes ]] || [ $(confirm) = true ]; then
  find . -name "*.class" -type f -delete
  echo -e "${red}\nDeleted Files!$reset"
fi
echo
