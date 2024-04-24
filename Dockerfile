FROM maven:3.9

WORKDIR /app

RUN groupadd -g 1000 app
RUN useradd -u 1000 -ms /bin/bash -g app app

USER app