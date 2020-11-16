package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class AsteroidCollisionable extends SquareCollisionable{

    Asteroid asteroid;

    public AsteroidCollisionable(int size, float angle, Vector2 position, Asteroid asteroid) {
        super(size, angle, position);
        this.asteroid=asteroid;
    }

    @Override
    public void collisionedWith(SquareCollisionable collisionable){
        collisionable.collisionedWithAsteroid(this);
    }

    @Override
    void collisionedWithProjectile(ProjectileCollisionable collisionable) {
        collisionable.projectile.destroy();
        this.asteroid.destroy();
    }

    @Override
    void collisionedWithShip(ShipCollisionable collisionable) {
        collisionable.starship.destroy();
    }

    @Override
    void collisionedWithAsteroid(AsteroidCollisionable collisionable) {
    }
}
