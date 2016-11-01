package aljourda.wolf3d;

import minilibX.Mlx;

public class Wolf3D {

	public static void main(String[] args) {
		final int width = 1024;
		final int height = width * 3 / 4;
		final Mlx window = new Mlx(width, height, "Wolf3D");
		final Map map = new Map();
		final Camera camera = new Camera(map);
		final Inputs inputs = new Inputs();
		inputs.wall = 1;
		final Ray ray = new Ray();
		
		Mlx.Hook hook = new Mlx.Hook() {

			@Override
			public void mouseWheel(int distance) {
			}

			@Override
			public void mouseMotion(int x, int y) {
			}

			@Override
			public void mouse(int code) {
			}

			@Override
			public void key(int keycode, boolean pressed) {
//				System.out.println(keycode);
				if (keycode == 27) {
					System.exit(0);
				}
				if (keycode == 107 && pressed) {
					camera.setFov(1);
				}
				if (keycode == 109 && pressed) {
					camera.setFov(-1);
				}
				if (keycode == 38) {
					inputs.up = pressed ? 1 : 0;
				}
				if (keycode == 40) {
					inputs.down = pressed ? 1 : 0;
				}
				if (keycode == 37) {
					inputs.left = pressed ? 1 : 0;
				}
				if (keycode == 39) {
					inputs.right = pressed ? 1 : 0;
				}
				if (keycode == 88) {
					inputs.wall = pressed ? 0 : 1;
				}
			}

			@Override
			public void expose() {
				window.clear();
				for (int x = 0; x < height / 2; x++) {
					window.drawLine(0, x, width, x, 0xB0E0E6);
				}
				for (int x = height / 2; x < height; x++) {
					window.drawLine(0, x, width, x, 0x095228);
				}
				
				for (int x = 0; x < width; x++) {
					double curPos = (double)x / (double)width;
					ray.initRay(camera, curPos);
					ray.initSideDist();
					ray.dda(map, inputs.wall);
					ray.display(window, x, height);
				}
			}

		};
		window.setHook(hook);
		
		long oldTime = System.currentTimeMillis();
		long time = oldTime;
		while(true){
			window.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			oldTime = time;
			time = System.currentTimeMillis();
		    double frameTime = (time - oldTime) / 1000.0;
//		    System.out.println(1.0 / frameTime);

			if(inputs.up == 1)
				camera.forward(map, frameTime * 3.0);
			if(inputs.down == 1)
				camera.backward(map, frameTime * 3.0);
			if(inputs.left == 1)
				camera.left(frameTime);
			if(inputs.right == 1)
				camera.right(frameTime);
		}

	}

}
