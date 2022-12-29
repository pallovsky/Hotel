from flask import Blueprint, request, jsonify
from flask_login import current_user, login_required

from backend.hotel import db
from backend.hotel.models.User import User, Game
from backend.hotel.response.responses import GameResponse
from backend.hotel.routes.helpers import created

games = Blueprint('games', __name__)


@games.route('/games')
@login_required
def get_games():
    game_models = User.query.filter_by(id=current_user.id).first().games
    game_responses = [GameResponse(game.id, game.name, len(game.users)).serialize() for game in game_models]

    return jsonify(game_responses)


@games.route('/games', methods=['POST'])
@login_required
def create_game():
    name = request.form.get('name')
    user_ids = request.form.get('userIds').split(',')

    users = User.query.filter(User.id.in_(user_ids)).all()

    new_game = Game(name=name, users=[])

    db.session.add(new_game)
    db.session.commit()

    return created("Game was successfully created.")
