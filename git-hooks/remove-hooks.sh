#!/bin/sh

TARGET_DIR=".git/hooks"

# Check if the hooks directory exists
if [ -d "$TARGET_DIR" ]; then
  echo "Removing Git hooks..."

  # Loop through each hook in the target directory
  for hook in "$TARGET_DIR"/*; do
    # Check if it is a file and remove it
    if [ -f "$hook" ]; then
      rm "$hook"
      echo "Removed hook: $(basename "$hook")"
    fi
  done

  echo "Git hooks have been removed."
else
  echo "No hooks directory found."
fi