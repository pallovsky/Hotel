
class UserResponse:
    def __init__(self, id, username, email, role, games_count):
        self.id = id
        self.username = username
        self.email = email
        self.role = role
        self.games_count = games_count

    def serialize(self):
        return {"id": self.id,
                "username": self.username,
                "email": self.email,
                "role": self.role,
                "gamesCount:": self.games_count
                }


class GameResponse:
    def __init__(self, _id, name, user_count):
        self.id = _id
        self.name = name
        self.user_count = user_count

    def serialize(self):
        return {"id": self.id,
                "name": self.name,
                "userCount": self.user_count
                }
