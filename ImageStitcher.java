import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfDMatch;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.features2d.ORB;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.DescriptorMatcher;

public class FeatureMatcher {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // Load images (These paths are placeholders, replace them with actual paths)
        Mat image1 = Imgcodecs.imread("path/to/first/image.jpg");
        Mat image2 = Imgcodecs.imread("path/to/second/image.jpg");

        Mat outputImage = matchFeatures(image1, image2);

        // Save or display the matched features image
        Imgcodecs.imwrite("path/to/output/matched_features.jpg", outputImage);
    }

    public static Mat matchFeatures(Mat img1, Mat img2) {
        // Initialize ORB detector
        ORB detector = ORB.create();

        // Detect keypoints
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        detector.detect(img1, keypoints1);
        detector.detect(img2, keypoints2);

        // Compute descriptors
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();
        detector.compute(img1, keypoints1, descriptors1);
        detector.compute(img2, keypoints2, descriptors2);

        // Match descriptors
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1, descriptors2, matches);

        // Draw matches
        Mat outputImg = new Mat();
        Features2d.drawMatches(img1, keypoints1, img2, keypoints2, matches, outputImg);

        return outputImg;
    }
}
