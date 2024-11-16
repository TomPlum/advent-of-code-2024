dependencies {
    "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.11.3")
    "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params:5.11.3")
    "testImplementation"("org.junit.platform:junit-platform-launcher:1.11.3")
    "testImplementation"("com.willowtreeapps.assertk:assertk-jvm:0.28.1")
}

tasks.withType<Test> {
    useJUnitPlatform { }
}