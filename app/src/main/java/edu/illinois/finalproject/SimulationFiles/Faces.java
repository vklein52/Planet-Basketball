package edu.illinois.finalproject.SimulationFiles;

/**
 * An old, rather poor style, class written to generate random faces - will use for the time being
 * and improve if time permits
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;

import java.io.ByteArrayOutputStream;

/*
 * Created by uytre_000 on 8/8/2016.
 */
public class Faces {

    private static int[] faceColors = new int[]{Color.parseColor("#823a00"), Color.parseColor("#692f01"),
            Color.parseColor("#3d1b00"), Color.parseColor("#ffe3ce"), Color.parseColor("#ffcca3"),
            Color.parseColor("#ffc18f"), Color.parseColor("#ffe98b"), Color.parseColor("#ffde54"),
            Color.parseColor("#ffe11a")};
    private static int[] eyeColors = new int[]{Color.parseColor("#0077ff"), Color.parseColor("#563212"),
            Color.parseColor("#6bbaa0"), Color.parseColor("#b8ba77")};
    private static int[] hairColors = new int[]{Color.parseColor("#7f4a00"), Color.parseColor("#683d00"),
            Color.parseColor("#452900"), Color.parseColor("#d0c600"), Color.parseColor("#9e9100"),
            Color.parseColor("#a0940a"), Color.parseColor("#000000"), Color.parseColor("#7f6500")};
    private static int[] weirdHairColors = {Color.MAGENTA, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.RED, Color.CYAN};

    public static byte[] makeFace(int height, int width) {

        int mult = RandomUtils.randInt(10, 10);
        ShapeDrawable hair = new ShapeDrawable(new OvalShape());
        hair.setIntrinsicWidth(width * RandomUtils.randInt(5, 8) / 10);
        hair.setIntrinsicHeight(width * RandomUtils.randInt(10, 16) / 20);
        if (RandomUtils.randInt(1, 150) == 1) {
            hair.getPaint().setColor(weirdHairColors[RandomUtils.randInt(0, weirdHairColors.length - 1)]);
        } else {
            hair.getPaint().setColor(hairColors[RandomUtils.randInt(0, hairColors.length - 1)]);
        }
        int hairTemp = RandomUtils.randInt(1, 1);
        int hairEdges = 0;
        switch (hairTemp) {
            case 0: {
                hairEdges = width / mult;
            }
            case 1: {
                hairEdges = RandomUtils.randInt(0, width / mult);

            }
        }


        ShapeDrawable head = new ShapeDrawable(new OvalShape());
        head.setIntrinsicHeight(height);
        head.setIntrinsicWidth(width);
        head.getPaint().setColor(faceColors[RandomUtils.randInt(0, faceColors.length - 1)]);


        int colorOfEye = eyeColors[RandomUtils.randInt(0, eyeColors.length - 1)];
        ShapeDrawable eyeBase = new ShapeDrawable(new OvalShape());
        eyeBase.setIntrinsicWidth(width / mult);
        eyeBase.setIntrinsicHeight(height / mult);
        eyeBase.getPaint().setColor(Color.WHITE);

        ShapeDrawable eyeColor = new ShapeDrawable(new OvalShape());
        eyeColor.setIntrinsicWidth(width / 2 / mult);
        eyeColor.setIntrinsicHeight(height / 2 / mult);
        eyeColor.getPaint().setColor(colorOfEye);

        ShapeDrawable eyePupil = new ShapeDrawable(new OvalShape());
        eyePupil.setIntrinsicWidth(width / 4 / mult);
        eyePupil.setIntrinsicHeight(height / 4 / mult);
        eyePupil.getPaint().setColor(Color.BLACK);

        ShapeDrawable eye2Base = new ShapeDrawable(new OvalShape());
        eye2Base.setIntrinsicWidth(width / mult);
        eye2Base.setIntrinsicHeight(height / mult);
        eye2Base.getPaint().setColor(Color.WHITE);
        ShapeDrawable eye2Color = new ShapeDrawable(new OvalShape());
        eye2Color.setIntrinsicWidth(width / 2 / mult);
        eye2Color.setIntrinsicHeight(height / 2 / mult);
        eye2Color.getPaint().setColor(colorOfEye);
        ShapeDrawable eye2Pupil = new ShapeDrawable(new OvalShape());
        eye2Pupil.setIntrinsicWidth(width / 4 / mult);
        eye2Pupil.setIntrinsicHeight(height / 4 / mult);
        eye2Pupil.getPaint().setColor(Color.BLACK);

        float angle;
        if (RandomUtils.randInt(0, 1) == 0)
            angle = 180;
        else
            angle = 360;
        ShapeDrawable mouth = new ShapeDrawable(new ArcShape(0, angle));
        mouth.setIntrinsicHeight(height * 3 / 2 / mult);
        mouth.setIntrinsicWidth(width * 7 / 2 / mult);
        mouth.getPaint().setColor(Color.WHITE);

        ShapeDrawable eyebrow = new ShapeDrawable(new RectShape());
        double eyebrowHeight = height * 10.0 / RandomUtils.randInt(100, 200);
        double eyebrowWidth = width * 10.0 / RandomUtils.randInt(50, 100);
        eyebrow.setIntrinsicHeight((int) eyebrowHeight);
        eyebrow.setIntrinsicWidth((int) eyebrowWidth);
        eyebrow.getPaint().setColor(hair.getPaint().getColor());
        ShapeDrawable eyebrow2 = new ShapeDrawable(new RectShape());
        eyebrow2.setIntrinsicHeight((int) eyebrowHeight);
        eyebrow2.setIntrinsicWidth((int) eyebrowWidth);
        eyebrow2.getPaint().setColor(hair.getPaint().getColor());

        ShapeDrawable tongue = null;
        boolean hasTongue = false;
        int tongueIndex = -1;
        double tongueMult = RandomUtils.randInt(48, 144) / 96.0;
        if (RandomUtils.randInt(0, 1) == 0) {
            hasTongue = true;
            tongue = new ShapeDrawable(new ArcShape(0, 180));
            tongue.setIntrinsicHeight(height * 3 / 2 / mult);
            tongue.setIntrinsicWidth(width * 7 / 4 / mult);
            tongue.getPaint().setColor(Color.RED);
        }

        //MUST BE LAST
        ShapeDrawable mustache = null;
        boolean hasMustache = false;
        int mustacheIndex = -1;
        if (RandomUtils.randInt(0, 3) == 0) {
            hasMustache = true;
            Shape shape = null;
            shape = new ArcShape(-360, -180);
            mustache = new ShapeDrawable(shape);
            mustache.setIntrinsicHeight(width / (10 * mult / 19));
            mustache.setIntrinsicWidth(width / (10 * mult / 19));
            mustache.getPaint().setColor(hair.getPaint().getColor());
        }
        Drawable[] layers;
        if (hasMustache && hasTongue) {
            Drawable[] temp = {hair, head, eyeBase, eyeColor, eyePupil, eyebrow, eye2Base, eye2Color, eye2Pupil, eyebrow2, mouth, tongue, mustache};
            tongueIndex = temp.length - 2;
            mustacheIndex = temp.length - 1;
            layers = temp;
        } else if (hasMustache) {

            Drawable[] temp = {hair, head, eyeBase, eyeColor, eyePupil, eyebrow, eye2Base, eye2Color, eye2Pupil, eyebrow2, mouth, mustache};
            mustacheIndex = temp.length - 1;
            layers = temp;
        } else if (hasTongue) {
            Drawable[] temp = {hair, head, eyeBase, eyeColor, eyePupil, eyebrow, eye2Base, eye2Color, eye2Pupil, eyebrow2, mouth, tongue};
            tongueIndex = temp.length - 1;
            layers = temp;
        } else {
            Drawable[] temp = {hair, head, eyeBase, eyeColor, eyePupil, eyebrow, eye2Base, eye2Color, eye2Pupil, eyebrow2, mouth};
            layers = temp;
        }
        LayerDrawable l = new LayerDrawable(layers);
        l.setLayerInset(0, hairEdges, 0, hairEdges, width / (mult / 5));
        l.setLayerInset(1, 0, width / mult, 0, 0);
        l.setLayerInset(2, width * 2 / mult, width * 10 / 3 / mult, width * 8 / mult, width * 15 / 2 / mult);
        l.setLayerInset(3, width * 2 / mult + width / (mult * 4), width * 10 / 3 / mult + width / (mult * 4), width * 8 / mult + width / (mult * 4), width * 15 / 2 / mult + width / (mult * 4));
        l.setLayerInset(4, width * 2 / mult + width / (mult * 2), width * 10 / 3 / mult + width / (mult * 2), width * 8 / mult + width / (mult * 2), width * 15 / 2 / mult + width / (mult * 2));
        l.setLayerInset(5, (int) ((width - eyebrowWidth) / 5), (int) (width * 10 / 3 / mult - eyebrowHeight), (int) ((width - eyebrowWidth) * 7 / 8), (int) (height * 15 / 2 / mult + 2.5 * eyebrowHeight));
        l.setLayerInset(6, width * 8 / mult, width * 10 / 3 / mult, width * 2 / mult, width * 15 / 2 / mult);
        l.setLayerInset(7, width * 8 / mult + width / (mult * 4), width * 10 / 3 / mult + width / (mult * 4), width * 2 / mult + width / (mult * 4), width * 15 / 2 / mult + width / (mult * 4));
        l.setLayerInset(8, width * 8 / mult + width / (mult * 2), width * 10 / 3 / mult + width / (mult * 2), width * 2 / mult + width / (mult * 2), width * 15 / 2 / mult + width / (mult * 2));
        l.setLayerInset(9, (int) ((width - eyebrowWidth) * 7 / 8), (int) (width * 10 / 3 / mult - eyebrowHeight), (int) ((width - eyebrowWidth) / 5), (int) (height * 15 / 2 / mult + 2.5 * eyebrowHeight));
        l.setLayerInset(10, width * 6 / (2 * mult), width * 7 / mult, width * 6 / (2 * mult), width * 3 / mult);
        if (hasTongue)
            l.setLayerInset(tongueIndex, (int) (width * 6 / 2 / mult + width * tongueMult / mult), width * 7 / mult, (int) (width * 6 / 2 / mult + width * tongueMult / mult), width * 3 / mult);
        if (hasMustache) {
            double mustacheMult = RandomUtils.randInt(1000, 3500) / 1000.0;
            l.setLayerInset(mustacheIndex, (int) (width * mustacheMult / (mult)), width * 7 / mult - width / 10, (int) (width * mustacheMult / (mult)), width - width * 7 / mult);
        }
        return bitmapToByteArray(drawableToBitmap(l));
    }

    public static Drawable byteArrayToDrawable(byte[] bytes, Context context) {
        Bitmap bitmap = byteArrayToBitmap(bytes);
        return bitmapToDrawable(bitmap, context);
    }

    private static Drawable bitmapToDrawable(Bitmap bitmap, Context context) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static Bitmap byteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
