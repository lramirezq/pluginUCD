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
def ambiente = props['ambiente']
print "Enviroment : " + ambiente
//example commandHelper
def workDir = new File(".");
def ch = new CommandHelper(workDir);
def sout = new StringBuffer(), serr = new StringBuffer()

//crear un archivo




f = new File('script.sh')


//Script

 def contenido = "broker=\$1                                                    \n" +
                 "exg=\$2                                                       \n" +
                 "enviroment=\$3                                                \n" +
                 "ls -ltr                                                       \n" +
                 "if [ -d 'BAR' ]                                               \n" +
                 "then                                                          \n" +
                 "    cd BAR                                                    \n" +
                 "        if [ -e \$enviroment*.properties ]                                                            \n" +
                 "        then                                                                                            \n"+
                 "            if [ -e *.bar ]                                                                            \n"+
                 "            then                                                                                        \n"+
                 "                mqsiapplybaroverride -b *.bar -p \$enviroment*.properties -o \$enviroment.bar            \n"+
                 "                mqsideploy \$broker -e \$exg -a \$enviroment.bar -w 1000                                \n"+
                 "                rm -f \$enviroment.bar                                                                \n"+
                 "                if [ \$? -eq 0 ]                                                                        \n" +
                 "                then                                                                                    \n"+
                 "                    exit \$?                                                                            \n"+
                 "                else                                                                                    \n"+
                 "                    echo 'Error in deploy'                                                            \n"+
                 "                    exit 1                                                                            \n"+
                 "                fi                                                                                    \n"+
                 "            else                                                                                        \n"+
                 "                echo 'There is not .bar artefact'                                                    \n"+
                 "                exit 1                                                                                \n"+
                 "            fi                                                                                        \n"+
                 "        else                                                                                            \n"+
                 "            echo 'Deploy sin properties'                                                                \n"+
                 "            if [ -e *.bar ]                                                                            \n"+
                 "            then                                                                                     \n"+
                 "                for n in *.bar; do                                                                     \n"+
                 "                mqsideploy \$broker -e \$exg -a \$n;                                            \n"    +
                 "                if [ \$? -eq 0 ]                                                                        \n"+
                 "                then                                                                                    \n"+
                 "                    exit \$?                                                                            \n"+
                 "                else                                                                                    \n"+
                 "                    echo 'Error in deploy'                                                            \n" +
                 "                    exit 1                                                                            \n"+
                 "                fi                                                                                    \n"+
                 "                done                                                                                    \n"+
                 "            else                                                                                        \n"+
                 "                echo 'There is not .bar artefact'                                                    \n"+
                 "                exit 1                                                                                \n"+
                 "            fi                                                                                        \n"+
                 "        fi                                                                                            \n"+
                 "else                                                                                                    \n"+
                 "        echo 'There is not such directory'                                                            \n"+
                 "        exit 1                                                                                        \n"+
                 "fi" 																																								

f.append(contenido)





def script = "chmod +x script.sh";
def script1 = "./script.sh " + broker + " " + exg + " " + ambiente		
//permiso archivos	
def command = script

 def proc = command.execute()
//exec shell
command = script1
proc =command.execute()
proc.waitFor()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)
println sout
println serr

//Set an output property
apTool.setOutputProperty("outPropName", "outPropValue");

apTool.storeOutputProperties();//write the output properties to the file
