public class RendererFactory {


	public RendererFactory() {

	}

	public Renderer buildRenderer(String type, int size) {
		Renderer renderer;
		switch (type.toLowerCase()) { //todo change
			case "none":
				renderer = new VoidRenderer();
				break;
			case "console":
				renderer = new ConsoleRenderer(size);
				break;
			default:
				renderer = null;
		}
		return renderer;
	}
}
