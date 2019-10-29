#!/usr/bin/env bash
#Run the bash crawler and the result store in file 'result_fille_path.json' in the user directory.

if [ "$#" -ne 1 ]; then
	echo  "Usage: $(basename "$0") {number_of_pages_to_fetch}"
	exit 1
fi

export BASH_CRAWLER_RESULT_FILE_PATH=$HOME/result_fille_path.json
cd ../../
sbt "run $1"
