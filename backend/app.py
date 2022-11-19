from flask import Flask

hotel = Flask(__name__)


@hotel.route('/')
def hello_geek():
    return '<h1>Hello from Flask & Docker</h2>'


if __name__ == "__main__":
    hotel.run(debug=True)
