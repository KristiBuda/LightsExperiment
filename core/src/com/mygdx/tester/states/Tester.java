package com.mygdx.tester.states;


import static com.mygdx.tester.handlers.B2Dhelpers.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.mygdx.tester.objects.Bodies;
import com.mygdx.tester.handlers.StateManager;
import com.mygdx.tester.main.MainTester;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;


public class Tester extends ApplicationState {


    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;

    private Body ground;
    private Body body;
    private Bodies objects;


    //creating the mouse joint
    private MouseJointDef jointDef;
    private MouseJoint joint;

    private RayHandler rayHandler;

    public Tester(StateManager gsm) {

        super(gsm);

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
//        boolean isStatic;
//        boolean isCircle;

        //set input adapter as the input processor
        Gdx.input.setInputProcessor(this);

        //create Objects
        objects = new Bodies();

        createScenery(objects);

        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, MainTester.V_WIDTH / PPM, MainTester.V_HEIGHT / PPM);

        //create ground
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(0, 0, 100, 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(bodyDef);
        ground.createFixture(groundShape, 0);
        groundShape.dispose();


        //mouse joint
        jointDef = new MouseJointDef();
        jointDef.bodyA = ground;
        //the one that we are moving arond
        jointDef.collideConnected = true;
        //the force that the max joint is going to apply
        jointDef.maxForce = 500;

        //lights go here
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(orthographicCamera.combined);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);
        rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.5f);
//        rayHandler.setShadows(false);

        int rays = 500;
        Math.round(rays / PPM);

        PointLight pointLight = new PointLight(rayHandler, rays, Color.WHITE, 10, 200 / PPM, 350/ PPM);
        pointLight.setXray(false);
//        box2dLight.DirectionalLight directionalLight;
//        directionalLight= new box2dLight.DirectionalLight(rayHandler, rays, Color.WHITE, -90);



    }

    public void handleInput() {
    }

    public void update(float dt) {
//        world.step(dt, 6, 2);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);
        debugRenderer.render(world, orthographicCamera.combined);

        rayHandler.updateAndRender();
    }


    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    //gets called if a fixture is clicked

    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;
            jointDef.bodyB = fixture.getBody();
            //set the fixture where the mouse is or the body that we want to move the texture
            jointDef.target.set(tmp.x, tmp.y);
            //(MouseJoint) so it knows we are using the mouse
            joint = (MouseJoint) world.createJoint(jointDef);

            return false;
        }
    };


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (joint == null)
            return false;
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));
        joint.setTarget(tmp2.set(tmp.x, tmp.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (joint == null)
            return false;

        world.destroyJoint(joint);
        //just to make sure
        joint = null;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //finding out if there has been a body that has been clicked
        //converting from clicked position to world position in box2D
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));
        //checking if i clicked an object
        world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
        return true;
    }


    public void createScenery(Bodies objects) {

        //static bodies
        //static in the middle

        objects.createBox(160, 100, 50, 13, true);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());

        //static left bound
        objects.createBox(0, 120, 1, MainTester.V_HEIGHT, true);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());

        //static right bound
        objects.createBox(MainTester.V_WIDTH, 120, 1, MainTester.V_HEIGHT, true);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());

//        //static top
//        objects.createBox(MainTester.V_WIDTH, mainTester.V_HEIGHT, MainTester.V_WIDTH, 1, true);
//        body = world.createBody(objects.getBodyDef());
//        body.createFixture(objects.getFixtureDef());

        //BOX dynamic bodies
        objects.createBox(160, 200, 5, 5, false);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());
        //
        objects.createBox(150, 150, 5, 5, false);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());

        //CIRCLE dynamic Bodies
        objects.createCircle(160, 200, 5, false);
        body = world.createBody(objects.getBodyDef());
        body.createFixture(objects.getFixtureDef());

    }


    public void dispose() {
        world.dispose();
        rayHandler.dispose();
    }

}
