package com.base.engine.game;

import com.base.engine.renderEngine.Camera;
import com.base.engine.renderEngine.Window;
import com.base.engine.utils.Input;
import com.base.engine.utils.Time;
import com.base.engine.utils.Transform;
import com.base.engine.utils.Vector2f;
import com.base.engine.utils.Vector3f;

public class Player {
	
	private static final float MOUSE_SENSITIVITY = 0.15f;
	private static final float MOVE_SPEED = 3.5f;
	private static final Vector3f zeroVector = new Vector3f(0,0,0);
	
	private Camera camera;
	private boolean mouseLocked = false;
	private Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
	private Vector3f movementVector;
	
	public Player(Vector3f position) {
		camera = new Camera(position, new Vector3f(0,0,1), new Vector3f(0,1,0));
		Transform.setCamera(camera);
		Transform.setProjection(70, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
	}
	
	public void input() {
		float movAmt = (float)(MOVE_SPEED * Time.getDelta());
		
		if(Input.getKey(Input.KEY_ESCAPE)) {
			Input.setCursor(true);
			mouseLocked = false;
		}
		
		if(Input.getMouseDown(0)) {
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		movementVector = zeroVector;
		
		if(Input.getKey(Input.KEY_W))
			movementVector = movementVector.add(camera.getForward());
		if(Input.getKey(Input.KEY_S))
			movementVector = movementVector.sub(camera.getForward());
		if(Input.getKey(Input.KEY_A))
			movementVector = movementVector.add(camera.getLeft());
		if(Input.getKey(Input.KEY_D))
			movementVector = movementVector.add(camera.getRight());
		
		movementVector.setY(0);
		if(movementVector.length() > 0) 
			movementVector = movementVector.normalized();
		
		camera.move(movementVector, movAmt);
		
		if(mouseLocked) {
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				camera.rotateY(deltaPos.getX() * MOUSE_SENSITIVITY);
			if(rotX)
				camera.rotateX(-deltaPos.getY() * MOUSE_SENSITIVITY);
				
			if(rotY || rotX)
				Input.setMousePosition(centerPosition);
		}
	}
	
	public void update() {
		
	}

	public void render() {
		
	}
	
	public Camera getCamera() {
		return camera;
	}
}