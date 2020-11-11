package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class ProjectileCollisionable extends SquareCollisionable{
    Projectile projectile;
    public ProjectileCollisionable(int size, float angle, Vector2 position, Projectile projectile) {
        super(size, angle, position);
        this.projectile=projectile;
    }

    @Override
    public void collisionedWith(SquareCollisionable collisionable) {
        collisionable.collisionedWithProjectile(this);
    }

    @Override
    void collisionedWithProjectile(ProjectileCollisionable collisionable) {
    }

    @Override
    void collisionedWithShip(ShipCollisionable collisionable) {
        if(!this.projectile.shipName.equals(collisionable.starship.shipName)) {
            collisionable.starship.destroy();
            this.projectile.destroy();
        }
    }
}
