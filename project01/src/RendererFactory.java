public class RendererFactory {


	public RendererFactory() {

	}
	public Renderer buildRenderer (String type, int size){
		Renderer renderer;
		switch (type.toLowerCase()) { //todo change
			case "whatever":
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
