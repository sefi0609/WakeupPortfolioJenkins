FROM alpine

# Install dependencies
RUN apk update
RUN apk add --no-cache \
    chromium \
    chromium-chromedriver \
    xvfb \
    python3

RUN python3 -m pip3 install --upgrade pip3
RUN pip3 install --no-cache-dir selenium

WORKDIR /app
COPY wakeup_portfolio.py ./wakeup_portfolio.py

CMD ["python", "wakeup_portfolio.py",  "--url", "https://protfolio-yosefi-kroytoro.streamlit.app/"]