# construction de l'image
custom_build(
    # nom de l'image
    ref = 'dispatcher-service',
    #commande pour créer l'image OCI avec Buildpacks
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    #fichiers / dossiers observés par Tilt pour déclencher un nouveau build en cas de mise à jour
    deps = ['build.gradle', 'src']
)

# déploiement dans K8s
k8s_yaml(['k8s/dispatcher_deployment.yaml', 'k8s/dispatcher_service.yaml'])

# port-forwarding
k8s_resource('dispatcher-service', port_forwards=['9003'])