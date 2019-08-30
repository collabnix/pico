#!/bin/bash
set -e
exec python3 image_processor.py &
exec python3 consumer.py
