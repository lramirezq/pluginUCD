/* This is an example step groovy to show the proper use of APTool
 * In order to use import these utilities, you have to use the "pluginutilscripts" jar
 * that comes bundled with this plugin example. 
 */
import com.urbancode.air.AirPluginTool;
import com.urbancode.air.CommandHelper;

/* This gets us the plugin tool helper. 
 * This assumes that args[0] is input props file and args[1] is output props file.
 * By default, this is true. If your plugin.xml looks like the example. 
 * Any arguments you wish to pass from the plugin.xml to this script that you don't want to 
 * pass through the step properties can be accessed using this argument syntax
 */
def apTool = new AirPluginTool(this.args[0], this.args[1])

/* Here we call getStepProperties() to get a Properties object that contains the step properties
 * provided by the user. 
 */
def props = apTool.getStepProperties();

/* This is how you retrieve properties from the object. You provide the "name" attribute of the 
 * <property> element 
 * 
 */

/**
 * Obtener las propiedades
 */
def dirOffset = props['dirOffset'];
def broker = props['broker'];
def exg = props['exg'];
def version = props['version'];


//example commandHelper
def workDir = new File(".");
def ch = new CommandHelper(workDir);
def sout = new StringBuffer(), serr = new StringBuffer()

//crear un archivo




f = new File('monitoreo.sh')




//Script for IIB 7
def contenido7 ="#!/bin/sh                          \n" +
		"broker=\$1                         \n" +
		"exg=\$2                            \n"+
		"barfile=`ls BAR/*.bar`             \n"+
		"flujos=`mqsireadbar -b \$barfile | grep cmf |  cut -d' ' -f 3  | sed -e 's/....\$//'` \n"+
		"tot_log=`mqsireadbar -b \$barfile | grep LoggingAdapter | wc -l`  \n"+
		"tot_error=`mqsireadbar -b \$barfile | grep ErrorAdapter | wc -l`  \n"+
		"echo \$tot_log \n"+
		"echo \$tot_error \n"+
		"echo \$flujo  \n"+
		"#Activar flujo principal \n"+

		"if [  -n \$flujos ]; then \n"+
		"    for f in \$flujos;     \n"+
		"    do                    \n"+
		"        mqsichangeflowmonitoring \$broker  -e \$exg -f \$f -c active; \n"+
		"    done \n"+
		"fi \n"+


		"if [ \$tot_log -gt 0 ]; then  \n"+
		"    for f in \$flujos;        \n" +
		"      do                      \n"+
		"       mqsichangeflowmonitoring \$broker -e \$exg -f  \$f -s 'LoggingAdapterv2.0.terminal.in' -i enable; \n"+
		"      done \n"+
		"fi \n"+


		"if [ \$tot_error -gt 0 ]; then  \n"+
		"   for f in \$flujos;   \n"+
		"    do                 \n"+
		"       mqsichangeflowmonitoring \$broker -e \$exg -f  \$f -s 'ErrorAdapterV2.0.terminal.in' -i enable; \n"+
		"    done \n  "+
		"fi"


def contenido10 ="#!/bin/sh                          \n" +
		"broker=\$1                         \n" +
		"exg=\$2                            \n"+
		"barfile=`ls BAR/*.bar`             \n"+
		"flujos=`mqsireadbar -b \$barfile | grep appzip | cut -d'.' -f 1  | tr -d '[[:space:]]'` \n"+
		"tot_log=`mqsireadbar -b \$barfile | grep LoggingAdapter | wc -l`  \n"+
		"tot_error=`mqsireadbar -b \$barfile | grep ErrorAdapter | wc -l`  \n"+
		"echo \$tot_log \n"+
		"echo \$tot_error \n"+
		"echo \$flujo  \n"+
		"#Activar flujo principal \n"+

		"if [  -n \$flujos ]; then \n"+
		"    for f in \$flujos;     \n"+
		"    do                    \n"+
		"        mqsichangeflowmonitoring \$broker  -e \$exg -f \$f -c active; \n"+
		"    done \n"+
		"fi \n"+


		"if [ \$tot_log -gt 0 ]; then  \n"+
		"    for f in \$flujos;        \n" +
		"      do                      \n"+
		"       mqsichangeflowmonitoring \$broker -e \$exg -f  \$f -s 'LoggingAdapterv2.0.terminal.in' -i enable; \n"+
		"      done \n"+
		"fi \n"+


		"if [ \$tot_error -gt 0 ]; then  \n"+
		"   for f in \$flujos;   \n"+
		"    do                 \n"+
		"       mqsichangeflowmonitoring \$broker -e \$exg -f  \$f -s 'ErrorAdapterV2.0.terminal.in' -i enable; \n"+
		"    done \n  "+
		"fi"


//si la version es 7 le ponemos contenido de script 7

if (version == "7"){
	f.append(contenido7)
}else{//si la version es <> 7 le ponemos contenido de 10
	f.append(contenido10)
}


def script = "chmod +x monitoreo.sh";
def script1 = "./monitoreo.sh " + broker + " " + exg
//permiso archivos
def command = script

def proc = command.execute()
//exec shell
command = script1
proc =command.execute()
proc.waitFor()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)
println "out> $sout err> $serr"

//Set an output property
apTool.setOutputProperty("outPropName", "outPropValue");

apTool.storeOutputProperties();//write the output properties to the file
