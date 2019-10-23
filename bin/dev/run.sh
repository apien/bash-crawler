#!/usr/bin/env bash
#Run the bash crawler and the result store in file 'result_fille_path.json' in the user directory.

export BASH_CRAWLER_RESULT_FILE_PATH=$HOME/result_fille_path.json
cd ../../
sbt run
