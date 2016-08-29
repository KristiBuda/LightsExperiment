package com.mygdx.tester.objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.mygdx.tester.handlers.B2Dhelpers.PPM;

/**
 * Created by buda on 8/29/16.
 */
public class Bodies {

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    public Bodies() {
        bodyDef      = new BodyDef();
        fixtureDef   = new FixtureDef();
    }

    public void createBox(float posX, float posY, float width, float height, boolean isStatic) {
        PolygonShape shape = new PolygonShape();
        bodyDef.position.set(posX / PPM, posY / PPM);

        if (isStatic == true) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
            fixtureDef.shape = shape;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            fixtureDef.shape = shape;
            fixtureDef.restitution = 0.7f;
            fixtureDef.density = 5;
        }

        shape.setAsBox(width / PPM, height / PPM);

    }

    public void createCircle(float posX, float posY, float radius, boolean isStatic) {

        CircleShape shape = new CircleShape();
        bodyDef.position.set(posX / PPM, posY / PPM);

        if (isStatic == true) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
            fixtureDef.shape = shape;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            fixtureDef.shape = shape;
            fixtureDef.restitution = 1;
            fixtureDef.density = 5;
        }
        shape.setRadius(radius / PPM);
    }

    public FixtureDef getFixtureDef() {return fixtureDef;}
    public BodyDef getBodyDef(){return bodyDef;}

}