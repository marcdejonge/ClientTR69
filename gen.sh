rm -Rf cpe
rm -Rf cpe.tar
mkdir cpe
mkdir cpe/data

mvn clean install -Dmaven.test.skip=true
cp ./StunClientBundle/target/*.jar ./cpe/StunClientBundle.jar
cp ./TR106Bundle/target/*.jar ./cpe/TR106Bundle.jar
cp ./ServerComBundle/target/*.jar ./cpe/ServerComBundle.jar
cp ./IPersistBundle/target/*.jar ./cpe/IPersistBundle.jar
cp ./FilePersistBundle/target/*.jar ./cpe/FilePersistBundle.jar
cp ./TR69ClientAPI/target/*.jar ./cpe/TR69ClientAPI.jar
cp ./ApplyBundle/target/*.jar ./cpe/ApplyBundle.jar
cp ./OSGiApplyBundle/target/*.jar ./cpe/OSGiApplyBundle.jar
cp ./AddObjectBundle/target/*.jar ./cpe/AddObjectBundle.jar
cp ./OSGIBundle/target/*.jar ./cpe/OSGIBundle.jar
cp ./DeleteObjectBundle/target/*.jar ./cpe/DeleteObjectBundle.jar
cp ./DownloadBundle/target/*.jar ./cpe/DownloadBundle.jar
cp ./GetParameterAttributesBundle/target/*.jar ./cpe/GetParameterAttributesBundle.jar
cp ./GetParameterNamesBundle/target/*.jar ./cpe/GetParameterNamesBundle.jar
cp ./GetParameterValuesBundle/target/*.jar ./cpe/GetParameterValuesBundle.jar
cp ./GetRPCMethodsBundle/target/*.jar ./cpe/GetRPCMethodsBundle.jar
cp ./RebootBundle/target/*.jar ./cpe/RebootBundle.jar
cp ./SetParameterAttributesBundle/target/*.jar ./cpe/SetParameterAttributesBundle.jar
cp ./SetParameterValuesBundle/target/*.jar ./cpe/SetParameterValuesBundle.jar
cp ./CSVDataBundle/target/*.jar ./cpe/CSVDataBundle.jar
cp ./Commons-codec/target/Commons-codec-bundle-1.3.jar ./cpe/.
#cp ./UPnPMDModelBundle/target/UPnPMDModelBundle-1.0.1-SNAPSHOT.jar ./cpe/.
#cp ./InfoServletApplyBundle/target/InfoServletApplyBundle-1.0.1-SNAPSHOT.jar ./cpe/.

cp ./bundles/JDK1.3-Util-0.0.1-SNAPSHOT.jar ./cpe/.


cp ./bundles/kxml-1.0.jar ./cpe/.
cp ./bundles/framework.jar ./cpe/.
cp ./bundles/log_all-2.0.2.jar ./cpe/.
cp ./bundles/cm_all-2.0.1.jar ./cpe/.
cp ./bundles/console_all-2.0.1.jar ./cpe/.
cp ./bundles/consoletty-2.0.1.jar ./cpe/.
cp ./bundles/component_all-2.0.0.jar ./cpe/.
cp ./bundles/event_all-2.0.4.jar ./cpe/.
cp ./bundles/frameworkcommands-2.0.5.jar ./cpe/.
cp ./bundles/com.francetelecom.upnp.basedriver-0.8.0-FTRDPATCHED.jar ./cpe/.
cp ./bundles/org.apache.felix.upnp.extra-0.4.0.jar ./cpe/.
cp ./bundles/upnp_api-2.0.0.jar ./cpe/.
cp ./bundles/net-java-Stun4j-0.0.1.jar ./cpe/.

cp ./ip.cfg  ./cpe/data/.
cp ./ClientTR69.sh  ./cpe/.
cp ./ClientTR69.sh  ./cpe/ClientTR69.bat
cp ./run.sh ./cpe/run.sh
cp ./init.def  ./cpe/init.xargs
cp ./CSV.def  ./cpe/CSV.cfg
cp ./CSVDataBundle/src/test/resources/device.ttx ./cpe/data/usine.txt
cp ./CSVDataBundle/src/test/resources/datamodel.csv ./cpe/data/datamodel.csv


