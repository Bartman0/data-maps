// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package nl.amsterdam.app;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.models.GeoBoundingBox;
import com.azure.maps.render.MapsRenderAsyncClient;
import com.azure.maps.render.MapsRenderClient;
import com.azure.maps.render.MapsRenderClientBuilder;
import com.azure.maps.render.models.MapImageStyle;
import com.azure.maps.render.models.MapStaticImageOptions;
import com.azure.maps.render.models.RasterTileFormat;
import com.azure.maps.render.models.StaticMapLayer;
import java.io.InputStream;
import java.io.FileOutputStream;

public class GetMapStaticImageSample {
    public static void main(String[] args) throws IOException {
        MapsRenderClientBuilder builder = new MapsRenderClientBuilder();

        // Authenticates using subscription key
        AzureKeyCredential keyCredential = new AzureKeyCredential(System.getenv("SUBSCRIPTION_KEY"));
        builder.credential(keyCredential);

        // Authenticates using Azure AD building a default credential
        // This will look for AZURE_CLIENT_ID, AZURE_TENANT_ID, and AZURE_CLIENT_SECRET env variables
        // DefaultAzureCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
        // builder.credential(tokenCredential);

        builder.mapsClientId(System.getenv("MAPS_CLIENT_ID"));
        builder.httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS));
        MapsRenderClient client = builder.buildClient();

        // Get Map Static Image
        // BEGIN: com.azure.maps.render.sync.get_map_static_image
        System.out.println("Get Map Static Image");
        GeoBoundingBox bbox = new GeoBoundingBox(4.721389126078996, 52.25463687577062, 4.921389126078996, 52.45463687577062);
        new StaticMapLayer();
        new RasterTileFormat();
        MapStaticImageOptions mapStaticImageOptions = new MapStaticImageOptions().setStaticMapLayer(StaticMapLayer.BASIC)
            .setMapImageStyle(MapImageStyle.MAIN).setZoom(2)
            .setBoundingBox(bbox).setRasterTileFormat(RasterTileFormat.PNG);
        BufferedInputStream rs1 = new BufferedInputStream(client.getMapStaticImage(mapStaticImageOptions).toStream(), 1_000_000);
        byte[] buffer1 = new byte[rs1.available()];
        rs1.read(buffer1);
        FileOutputStream os1 = new FileOutputStream("image1.png");
        os1.write(buffer1);
        os1.close();
        // END: com.azure.maps.render.sync.get_map_static_image

        MapsRenderClientBuilder asyncClientbuilder = new MapsRenderClientBuilder();

        // Authenticates using subscription key
        AzureKeyCredential asyncClientKeyCredential = new AzureKeyCredential(System.getenv("SUBSCRIPTION_KEY"));
        asyncClientbuilder.credential(asyncClientKeyCredential);

        // Authenticates using Azure AD building a default credential
        // This will look for AZURE_CLIENT_ID, AZURE_TENANT_ID, and AZURE_CLIENT_SECRET env variables
        // DefaultAzureCredential asyncClientTokenCredential = new DefaultAzureCredentialBuilder().build();
        // asyncClientbuilder.credential(asyncClientTokenCredential);

        asyncClientbuilder.mapsClientId(System.getenv("MAPS_CLIENT_ID"));
        asyncClientbuilder.httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS));
        MapsRenderAsyncClient asyncClient = asyncClientbuilder.buildAsyncClient();

        // Get Map Static Image
        // BEGIN: com.azure.maps.render.async.get_map_static_image
        System.out.println("Get Map Static Image");
        GeoBoundingBox bbox2 = new GeoBoundingBox(4.721389126078996, 52.25463687577062, 4.921389126078996, 52.45463687577062);
        new StaticMapLayer();
        new RasterTileFormat();
        MapStaticImageOptions mapStaticImageOptions2 = new MapStaticImageOptions().setStaticMapLayer(StaticMapLayer.BASIC)
            .setMapImageStyle(MapImageStyle.MAIN).setZoom(10)
            .setBoundingBox(bbox2).setRasterTileFormat(RasterTileFormat.PNG);
        asyncClient.getMapStaticImage(mapStaticImageOptions2).block().toStream();
        BufferedInputStream rs2 = new BufferedInputStream(client.getMapStaticImage(mapStaticImageOptions2).toStream(), 1_000_000);
        byte[] buffer2 = new byte[rs2.available()];
        rs2.read(buffer2);
        FileOutputStream os2 = new FileOutputStream("image2.png");
        os2.write(buffer2);
        os2.close();
        // END: com.azure.maps.render.async.get_map_static_image
    }
}
