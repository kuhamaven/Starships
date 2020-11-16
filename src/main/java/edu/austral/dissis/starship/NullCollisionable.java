package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class NullCollisionable extends SquareCollisionable{

    public NullCollisionable() {
        super(0,0, Vector2.vector(0,0));
    }

    @Override
    void collisionedWithProjectile(ProjectileCollisionable collisionable) {

    }

    @Override
    void collisionedWithShip(ShipCollisionable collisionable) {

    }

    @Override
    void collisionedWithAsteroid(AsteroidCollisionable collisionable) {

    }
}
