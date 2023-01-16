import javax.sound.sampled.FloatControl;

public class Camera {
    Matrix2d location; // where is camera
    Matrix2d direction; // camera points
    double fieldOfView; // theta (FOV) in degrees
    double invTanTheta; // inverse of tan of FOV
    public Camera(){
        // default constructor
        location = new Matrix2d(new double[][]{{-8},{-1},{2}});
        direction = new Matrix2d(new double[][]{{8},{1},{-0.5}});
        fieldOfView = 135;
        invTanTheta = 1/Math.tan(Math.toRadians(fieldOfView/2));
    }
    public Camera(Matrix2d location,Matrix2d direction,double fieldOfView){
        this.location = location;
        this.direction = direction;
        this.fieldOfView = fieldOfView;
        invTanTheta = 1/Math.tan(Math.toRadians(fieldOfView/2));
    }
}
