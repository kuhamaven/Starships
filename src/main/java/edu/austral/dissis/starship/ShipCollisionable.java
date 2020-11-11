package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class ShipCollisionable extends SquareCollisionable{
    Starship starship;
    public ShipCollisionable(int size, float angle, Vector2 position, Starship starship) {
        super(size, angle, position);
        this.starship = starship;
    }

    @Override
    public void collisionedWith(SquareCollisionable collisionable){
        collisionable.collisionedWithShip(this);
    }

    @Override
    void collisionedWithProjectile(ProjectileCollisionable collisionable) {
        if(!collisionable.projectile.shipName.equals(starship.shipName)) {
            collisionable.projectile.destroy();
            this.starship.destroy();
        }
    }

    @Override
    void collisionedWithShip(ShipCollisionable collisionable) {
        collisionable.starship.destroy();
        this.starship.destroy();
    }

    @Override
    void collisionedWithAsteroid(AsteroidCollisionable collisionable) {
        collisionable.asteroid.destroy();
        this.starship.destroy();
    }
}
