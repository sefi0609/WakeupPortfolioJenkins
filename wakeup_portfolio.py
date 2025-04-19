import argparse
from time import sleep
from datetime import datetime
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


def wake_up_streamlit_app(url: str) -> str:
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--disable-gpu')  # Disable GPU acceleration
    options.add_argument('--no-sandbox')  # Bypass OS security model (needed for Docker)
    options.add_argument('--disable-dev-shm-usage')  # Overcome limited resources in containers
    options.add_argument('--user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"')

    driver = webdriver.Chrome(options=options)

    driver.get(url)

    with open('logs.log', 'a') as log_file:
        if 'streamlit' not in url:
            log_file.write(f"{datetime.now()} - This URL is not a Streamlit application\n")
            return 'NotAStreamlitApp'

        try:
            # Wait for the button to load completely
            WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, '//button [contains(text(), "Yes, get this app back up!")]')))

            # Find and click the button to wake up the app
            auth_button = driver.find_element(By.XPATH, '//button [contains(text(), "Yes, get this app back up!")]')
            auth_button.click()

            # Simulate a user interaction
            sleep(10)

            log_file.write(f"{datetime.now()} - Waking up Streamlit application...\n")
            return 'WakingUpStreamlitApp'

        except Exception as e:
            log_file.write(f"{datetime.now()} - An error occurred: {e}\n")
            return 'CanNotWakeUpStreamlitApp'

        finally:
            driver.quit()


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--url', help='URL for the streamlit application')
    args = parser.parse_args()

    ref = wake_up_streamlit_app(args.url)

    match ref:
        case 'NotAStreamlitApp':
            print('This URL is not a Streamlit application')
        case 'WakingUpStreamlitApp':
            print('The Streamlit application is waking up...')
        case _:
            print('The Streamlit application is already up OR not responding')