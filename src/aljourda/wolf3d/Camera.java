package aljourda.wolf3d;

public class Camera {
	double posX;
	double posY;
	double dirX;
	double dirY;
	double deltaX;
	double deltaY;
	double fov;

	public Camera(Map map) {
		fov = 0;
		posX = 2;
		posY = 2;
		dirX = 1;
		dirY = 0;
		deltaX = 0;
		deltaY = 0;
		setFov(66);
	}

	public void setFov(double fov) {
		this.fov += fov;
		if(this.fov < 10 || this.fov > 170){
			this.fov -= fov;
		}else{
			dirX = 1;
			dirY = 0;
			deltaX = 0;
			deltaY = Math.tan((this.fov * Math.PI / 180.0) / 2);
//			System.out.println(this.fov);
		}
	}
	
	public void forward(Map map, double speed) {
		int x = (int) (posX + dirX * speed);
		int y = (int) (posY + dirY * speed);
		if (map.get(x, (int) posY) == 0 && map.get((int) posX, y) == 0) {
			posX += dirX * speed;
			posY += dirY * speed;
		}
	}

	public void backward(Map map, double speed) {
		int x = (int) (posX - dirX * speed);
		int y = (int) (posY - dirY * speed);
		if (map.get(x, (int) posY) == 0 && map.get((int) posX, y) == 0) {
			posX -= dirX * speed;
			posY -= dirY * speed;
		}
	}

	public void left(double speed) {
		double oldDirX = dirX;
		dirX = dirX * Math.cos(-speed) - dirY * Math.sin(-speed);
		dirY = oldDirX * Math.sin(-speed) + dirY * Math.cos(-speed);
		double oldDeltaX = deltaX;
		deltaX = deltaX * Math.cos(-speed) - deltaY * Math.sin(-speed);
		deltaY = oldDeltaX * Math.sin(-speed) + deltaY * Math.cos(-speed);
	}

	public void right(double speed) {
		double oldDirX = dirX;
		dirX = dirX * Math.cos(speed) - dirY * Math.sin(speed);
		dirY = oldDirX * Math.sin(speed) + dirY * Math.cos(speed);
		double oldDeltaX = deltaX;
		deltaX = deltaX * Math.cos(speed) - deltaY * Math.sin(speed);
		deltaY = oldDeltaX * Math.sin(speed) + deltaY * Math.cos(speed);
	}

}
