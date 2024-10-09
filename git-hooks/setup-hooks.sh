#!/bin/sh

HOOKS_DIR="git-hooks"
TARGET_DIR=".git/hooks"

# List of files to ignore
IGNORE_FILES=("setup-hooks.sh" "remove-hooks.sh" "README.md")

if [ -d "$HOOKS_DIR" ]; then
  for hook in "$HOOKS_DIR"/*; do
    hook_name=$(basename "$hook")

    # Check if the current hook is in the ignore list
    if [[ " ${IGNORE_FILES[*]} " == *" $hook_name "* ]]; then
      continue
    fi

    # Copy hook file
    if cp "$hook" "$TARGET_DIR/$hook_name"; then
      chmod +x "$TARGET_DIR/$hook_name"
      echo "Set up hook: $hook_name"
    else
      echo "Failed to copy hook: $hook_name"
    fi
  done
  echo "Git hooks have been set up."
else
  echo "No hooks directory found."
fi