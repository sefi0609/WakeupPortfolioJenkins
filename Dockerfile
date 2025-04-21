FROM alpine

# Install dependencies
RUN apk update
RUN apk add --no-cache \
    chromium \
    chromium-chromedriver \
    xvfb \
    python3

RUN python -m pip install --upgrade pip
RUN pip install --no-cache-dir selenium

WORKDIR /app
COPY wakeup_portfolio.py ./wakeup_portfolio.py

CMD ["python", "wakeup_portfolio.py",  "--url", "https://protfolio-yosefi-kroytoro.streamlit.app/"]