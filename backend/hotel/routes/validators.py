import re

from backend.hotel.models.User import User
from backend.hotel.routes.helpers import bad_request


EMAIL_REGEX = re.compile(r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+')


class ValidationResult:
    def __init__(self, result, error_response=None):
        self.success = result
        self.error_response = error_response


def validate_register_request(email, username, password):
    user = User.query.filter_by(email=email).first()
    if user:
        return ValidationResult(False, bad_request("User with this email already exist in database."))

    if not email or not (re.fullmatch(EMAIL_REGEX, email)):
        return ValidationResult(False, bad_request("Incorrect email."))

    if not username or len(username) < 3 or len(username) > 20:
        return ValidationResult(False, bad_request("Incorrect username."))

    if not password or len(password) < 5:
        return ValidationResult(False, bad_request("Too short password."))

    return ValidationResult(True)
