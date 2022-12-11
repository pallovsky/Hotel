from flask import jsonify


def ok(message):
    response = jsonify({'message': message})
    response.status_code = 200
    return response


def created(message):
    response = jsonify({'message': message})
    response.status_code = 201
    return response


def bad_request(message):
    response = jsonify({'message': message})
    response.status_code = 400
    return response


def unauthorized(message):
    response = jsonify({'message': message})
    response.status_code = 401
    return response
