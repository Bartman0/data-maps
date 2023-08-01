import os
from azure.core.credentials import AzureKeyCredential
from azure.maps.render import MapsRenderClient
from azure.maps.render.models import BoundingBox
import sys
import logging

# Create a logger for the 'azure.maps.render' SDK
logger = logging.getLogger('azure.maps.render')
logger.setLevel(logging.DEBUG)

# Configure a console output
handler = logging.StreamHandler(stream=sys.stdout)
logger.addHandler(handler)

credential = AzureKeyCredential(os.environ.get("AZURE_SUBSCRIPTION_KEY"))

render_client = MapsRenderClient(
    credential=credential
)

# result = render_client.get_map_static_image(img_format="png", 
                                            # layer="basic",
                                            # zoom=10, 
                                            # bounding_box_private = BoundingBox(
                                            # 52.25463687577062, 4.721389126078996, 52.45463687577062, 4.921389126078996)
                                            # # 4.721389126078996, 52.25463687577062, 4.921389126078996, 52.45463687577062)
                                            # # center=(4.821389126078996, 52.35463687577062)

result = render_client.get_map_static_image(
        img_format="png",
        layer="basic",
        style="dark",
        zoom=10,
        bounding_box_private= BoundingBox(
            13.228, 52.4559, 13.5794, 52.629
        )
    )

#print(next(result))
# Save result to file as png
file = open('result.png', 'wb')
file.write(next(result))
file.close()
