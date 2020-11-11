package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.collision.Collisionable;
import edu.austral.dissis.starship.base.vector.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class SquareCollisionable implements Collisionable<SquareCollisionable> {
    private final float size;
    private final float angle;
    private final Vector2 position;

    public SquareCollisionable(int size, float angle, Vector2 position) {
        this.size = size;
        this.angle = angle;
        this.position = position;
    }

    @Override
    public Shape getShape() {
        final Rectangle2D baseSquare = new Rectangle2D.Float(size / -2, size / -2, size, size);
        final Path2D.Float path = new Path2D.Float();
        path.append(baseSquare, false);

        final AffineTransform transform = new AffineTransform();
        transform.translate(position.getX(), position.getY());
        transform.rotate(angle);

        path.transform(transform);

        return path;
    }

    @Override
    public void collisionedWith(SquareCollisionable collisionable) {
        System.out.println("Collisioned with " + collisionable);
    }
}
