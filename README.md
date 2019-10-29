# Bash crawler

Application allows to fetch messages from http://bash.org.pl/. It fetch messages from a given number of pages. It stores the fetched
messages in the local file.

## Getting started

### Prerequisites

* Scala SBT [here](https://www.scala-sbt.org/)
* Java 11

### RUN
sbt -Dconfig.file=/home/arek/projects/bash-crawler/bin/dev/conf/development.conf
sbt -Dbashcrawler.result-file-path=/home/arek/bash_crawler_result.json

##### Bash script  
Run prepared `run.sh` which takes single argument - number of pages to fetch  i.e: ./run.sh 34.

##### Directly run sbt command
`sbt -Dbashcrawler.result-file-path={result_file_path} "run {pages_number}"` so example command looks like
`sbt -Dbashcrawler.result-file-path=/home/arek/bash_crawler_result.json "run 5"`

## Application configuration

|env variable|system property|description|default value|example value|
|---|---|---|---|---|
| BASH_CRAWLER_RESULT_FILE_PATH | bashcrawler.result-file-path | It contains a path to file with extracted data. | - | /home/john_doe/result.json |
| BASH_CRAWLER_BASH_URL | bashcrawler.bash-url | Base url to bash - source of our information. | http://bash.org.pl | http://bash.org.pl |


