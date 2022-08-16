import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;



/**
* An example for loading a geometric object and showing it in the scene.
* The names of the parts of the object are printed.
* The colour blue is assigned to one of the part.
*
* @author Frank Klawonn
* Last change 07.07.2005
*/
public class Load3DExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public Load3DExample()
  {

    //Mechanism for closing the window and ending the program.
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //Default settings for the viewer parameters.
    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());


    //Construct the SimpleUniverse:
    //First generate it using the Canvas.
    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);


    //Default position of the viewer.
    simpUniv.getViewingPlatform().setNominalViewingTransform();


    //The scene is generated in this method.
    createSceneGraph(simpUniv);


    //Add some light to the scene.
    addLight(simpUniv);


    //The following three lines enable navigation through the scene using the mouse.
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("An object loaded from a file");
    setSize(1024,720);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);


  }




  public static void main(String[] args)
  {
     Load3DExample le = new Load3DExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {



    //Load an obj-file.
    ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
    Scene s = null;
	ObjectFile f1 = new ObjectFile(ObjectFile.RESIZE);
    Scene s1 = null;
    // ASH
    try
    {
      s = f.load("Ash.obj");
    }
    catch (Exception e)
    {
      System.out.println("File loading failed:" + e);
    }
	
	Transform3D ash = new Transform3D();
    //ash.rotZ(1.7*Math.PI);
    //ash.rotX(1.8*Math.PI);
    ash.rotY(2.7*Math.PI);
    Transform3D RotateY = new Transform3D();
    RotateY.rotX((2.5*Math.PI));
    
    ash.mul(RotateY);
    
    ash.setTranslation(new Vector3f(-1.0f,-1.2f,1.0f));
    TransformGroup ashgrupo = new TransformGroup(ash);
    ashgrupo.addChild(s.getSceneGroup());
    //BOLA
    
    //bolinha
    Appearance Vermelho = new Appearance();
    setToMyDefaultAppearance(Vermelho,new Color3f(0.7f,0.f,0.f));
    
    //bolinha
    Sphere Bolinha = new Sphere(0.017f,Sphere.GENERATE_NORMALS,100,Vermelho);
	
	Transform3D trans_Bola = new Transform3D();
	trans_Bola.setTranslation(new Vector3f(-0.2f,-0.2f,-0.2f));
	
	TransformGroup bola = new TransformGroup(trans_Bola);
	bola.addChild(Bolinha);
	Transform3D anda_Bola = new Transform3D();
	anda_Bola.setTranslation(new Vector3f(3.10f,3.1f,6.70f));
	
	
	TransformGroup mov_bola = new TransformGroup(anda_Bola);
	
    
    Alpha alpha1 = new Alpha(1,Alpha.INCREASING_ENABLE,2000,0,2000,300,0,0,0,0);

	PositionInterpolator move = new PositionInterpolator(alpha1,bola,anda_Bola,-0.2f,0.3f);
	
	BoundingSphere boundstest1 = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
	
	move.setSchedulingBounds(boundstest1);

	bola.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	bola.addChild(move);

	
	//POKEMON
	try
    {
      s1 = f1.load("Pokemon.obj");
    }
    catch (Exception e)
    {
      System.out.println("File loading failed:" + e);
    }
    
    Transform3D poke = new Transform3D();
    poke.rotY(2.7*Math.PI);
    Transform3D RotateY1 = new Transform3D();
    RotateY1.rotX((2.5*Math.PI));
    TransformGroup pokegrupo = new TransformGroup(poke);
    pokegrupo.addChild(s1.getSceneGroup());

		//CABO

    Transform3D axis = new Transform3D();
	axis.rotY(5.9*Math.PI);
    Transform3D RotateY2 = new Transform3D();
    RotateY2.rotX((6.9*Math.PI));

	Alpha alpha = new Alpha(1,Alpha.INCREASING_ENABLE,0,2000,2000,0,0,0,0,0);

	PositionInterpolator pi =new PositionInterpolator(alpha,pokegrupo,axis,1.5f,0.5f);
	
	BoundingSphere boundstest = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
	
	pi.setSchedulingBounds(boundstest);

	pokegrupo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	pokegrupo.addChild(pi);

	Transform3D tfum = new Transform3D();
    tfum.rotZ(4.95*Math.PI);
    Transform3D tfdois = new Transform3D();
    tfdois.rotY(1.8*Math.PI);
    tfdois.setTranslation(new Vector3f(3.1f,1.3f,0.7f));
    tfum.mul(tfdois);
	TransformGroup tgfinal = new TransformGroup (tfdois);

	tgfinal.addChild(pokegrupo);
	
	Transform3D scale = new Transform3D();
	scale.setScale(0.2f);
	TransformGroup pokescale = new TransformGroup(scale);
	TransformGroup ashscale = new TransformGroup(scale);
	ashscale.addChild(tgfinal);
	ashscale.addChild(ashgrupo);
	
    BranchGroup theScene = new BranchGroup();

    theScene.addChild(pokescale);
    

    //The following four lines generate a white background.

    //The bounding region for the background.
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    //Load the background image.
    TextureLoader textureLoad = new TextureLoader("fundo.png",null);
    //Define the image as the background and add it to the scene.
    Background bgImage = new Background(textureLoad.getImage());
    bgImage.setApplicationBounds(bounds);
    theScene.addChild(bgImage);
    theScene.addChild(ashscale);
    theScene.addChild(bola);
    

    theScene.compile();

    //Add the scene to the universe.
    su.addBranchGraph(theScene);
  }


  /**
  * Generates a default surface (Appearance) in a specified colour.
  *
  * @param app      The Appearance for the surface.
  * @param col      The colour.
  */
  public static void setToMyDefaultAppearance(Appearance app, Color3f col)
  {
    app.setMaterial(new Material(col,col,col,col,150.0f));
  }



  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  }



}

