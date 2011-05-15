#svn update
#mvn clean install
rm -Rf cpe
rm -Rf cpe.tar
mkdir cpe
mkdir cpe/data


cd ./StunClientBundle/
mvn clean install
cd ../TR106Bundle/
mvn clean install
cd ../ServerComBundle/
mvn clean install
cd ../IPersistBundle/
mvn clean install
cd ../FilePersistBundle/
mvn clean install
cd ../TR69ClientAPI/
mvn clean install
cd ../ApplyBundle/
mvn clean install
cd ../OSGiApplyBundle/
mvn clean install
cd ../AddObjectBundle/
mvn clean install
cd ../OSGIBundle/
mvn clean install
cd ../DeleteObjectBundle/
mvn clean install
cd ../DownloadBundle/
mvn clean install
cd ../GetParameterAttributesBundle/
mvn clean install
cd ../GetParameterNamesBundle/
mvn clean install
cd ../GetParameterValuesBundle/
mvn clean install
cd ../GetRPCMethodsBundle/
mvn clean install
cd ../RebootBundle/
mvn clean install
cd ../SetParameterAttributesBundle/
mvn clean install
cd ../SetParameterValuesBundle/
mvn clean install
cd ../CSVDataBundle/
mvn clean install
cd ../Commons-codec/
mvn clean install
cd ../UPnPMDModelBundle/
mvn clean install
cd ../InfoServletApplyBundle/
mvn clean install


cp ./StunClientBundle/target/StunClientBundle-*.jar ./cpe/.
cp ./TR106Bundle/target/TR106Bundle-*.jar ./cpe/.
cp ./ServerComBundle/target/ServerComBundle-*.jar ./cpe/.
cp ./IPersistBundle/target/IPersistBundle-*.jar ./cpe/.
cp ./FilePersistBundle/target/FilePersistBundle-*.jar ./cpe/.
cp ./TR69ClientAPI/target/TR69ClientAPI-*.jar ./cpe/.
cp ./ApplyBundle/target/ApplyBundle-*.jar ./cpe/.
cp ./OSGiApplyBundle/target/OSGiApplyBundle-*.jar ./cpe/.
cp ./AddObjectBundle/target/AddObjectBundle-*.jar ./cpe/.
cp ./OSGIBundle/target/OSGIBundle-*.jar ./cpe/.
cp ./DeleteObjectBundle/target/DeleteObjectBundle-*.jar ./cpe/.
cp ./DownloadBundle/target/DownloadBundle-*.jar ./cpe/.
cp ./GetParameterAttributesBundle/target/GetParameterAttributesBundle-*.jar ./cpe/.
cp ./GetParameterNamesBundle/target/GetParameterNamesBundle-*.jar ./cpe/.
cp ./GetParameterValuesBundle/target/GetParameterValuesBundle-*.jar ./cpe/.
cp ./GetRPCMethodsBundle/target/GetRPCMethodsBundle-*.jar ./cpe/.
cp ./RebootBundle/target/RebootBundle-*.jar ./cpe/.
cp ./SetParameterAttributesBundle/target/SetParameterAttributesBundle-*.jar ./cpe/.
cp ./SetParameterValuesBundle/target/SetParameterValuesBundle-*.jar ./cpe/.
cp ./CSVDataBundle/target/CSVDataBundle-*.jar ./cpe/.
cp ./Commons-codec/target/Commons-codec-bundle-1.3.jar ./cpe/.
cp ./UPnPMDModelBundle/target/UPnPMDModelBundle-1.0.1-SNAPSHOT.jar ./cpe/.
cp ./InfoServletApplyBundle/target/InfoServletApplyBundle-1.0.1-SNAPSHOT.jar ./cpe/.

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

tar cvf cpe.tar cpe
#cp cpe.tar /net/g-odisfiler/mnt/filer/gregory
