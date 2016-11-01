package aljourda.wolf3d;

import minilibX.Mlx;

public class Ray {
	private double posX;
	private double posY;
	private double dirX;
	private double dirY;
	private int mapX;
	private int mapY;
	private double sideX;
	private double sideY;
	private double deltaX;
	private double deltaY;
	private double wallDist;
	private int stepX;
	private int stepY;
	private int color;
	
	//Initialize ray
	public void initRay(Camera camera, double curPos){
		posX = camera.posX;
		posY = camera.posY;
		dirX = camera.dirX + camera.deltaX * ( 2 * curPos - 1);
		dirY = camera.dirY + camera.deltaY * ( 2 * curPos - 1);
		mapX = (int) (posX);
		mapY = (int) (posY);
		deltaX = Math.sqrt((dirY * dirY) / (dirX * dirX) + 1);
		deltaY = Math.sqrt((dirX * dirX) / (dirY * dirY) + 1);
	}

	//Initialize sideDist
	public void initSideDist(){
		if (dirX < 0) {
			stepX = -1;
			sideX = (posX - mapX) * deltaX;
		} else {
			stepX = 1;
			sideX = (mapX - posX + 1.0) * deltaX;
		}
		if (dirY < 0) {
			stepY = -1;
			sideY = (posY - mapY) * deltaY;
		} else {
			stepY = 1;
			sideY = (mapY - posY + 1.0) * deltaY;
		}
	}
	
	//Digital Differential Analyzer
	public void dda(Map map, int wallVisible){
		int side = 0;
		int hit = 0;
		while (hit == 0) {
			if (sideX < sideY) {
				sideX += deltaX;
				mapX += stepX;
				side = 0;
			} else {
				sideY += deltaY;
				mapY += stepY;
				side = 1;
			}
			if (map.get(mapX, mapY) > wallVisible)
				hit = 1;
		}
		if (side == 0)
			wallDist = (mapX - posX + (1 - stepX) / 2) / dirX;
		else
			wallDist = (mapY - posY + (1 - stepY) / 2) / dirY;
		color = map.getColor(mapX, mapY, side);
	}
	
	//Distance calculation and display
	public void display(Mlx window, int x, int height){
		int lineHeight = (int) (height / wallDist);
		int start = -lineHeight / 2 + height / 2;
		if (start < 0)
			start = 0;
		int end = lineHeight / 2 + height / 2;
		if (end >= height)
			end = height - 1;
		window.drawLine(x, start, x, end, color);
	}
}
