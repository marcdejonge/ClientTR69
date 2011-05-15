/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.tr157.implem;
import java.util.Observable;
import java.util.Observer;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.TemperatureSensor;

public class TempSensorImp extends TemperatureSensor implements Observer {
    static final int UNDEFINED_TEMP = -274;
    public TempSensorImp(String name, IParameterData data, String basePath) {
        super(data, basePath);
    }
    public void initialize() throws Fault {
        super.initialize();
        getParamReset().addObserver(this);
        getParamValue().addObserver(this);
        getParamStatus().addObserver(this);
    }
    public void update(Observable parameter, Object arg) {
        if (parameter == getParamValue()) {
            onValueChange((Parameter) parameter);
        } else if (parameter == getParamReset()) {
            onReset((Parameter) parameter);
        } else if (parameter == getParamStatus()) {
            onStatusChange((Parameter) parameter);
        }
    }
    private void onReset(Parameter parameter) {
        if (Boolean.TRUE.equals(parameter.getValue())) {
            long time = System.currentTimeMillis();
            getParamLowAlarmTime().setDirectValue(new Integer(-257));
            getParamHighAlarmTime().setDirectValue(new Integer(-257));
            getParamResetTime().setDirectValue(new Long(time));
            parameter.setDirectValue(Boolean.FALSE);
        }
    }
    private void onValueChange(Parameter parameter) {
        int value = ((Integer) parameter.getValue()).intValue();
        int lowAlarmValue = ((Integer) getParamLowAlarmValue().getValue())
                .intValue();
        int highAlarmValue = ((Integer) getParamHighAlarmValue()
                .getValue()).intValue();
        int minValue = ((Integer) getParamMinValue().getValue())
                .intValue();
        int maxValue = ((Integer) getParamMaxValue().getValue())
                .intValue();
        Long zero = new Long(0);
        Long time = new Long(System.currentTimeMillis());
        getParamLastUpdate().setDirectValue(time);
        //
        if (value < minValue) {
            getParamMinValue().setDirectValue(new Integer(value));
            getParamMinTime().setDirectValue(time);
        }
        //
        if (value > maxValue) {
            getParamMaxValue().setDirectValue(new Integer(value));
            getParamMaxTime().setDirectValue(time);
        }
        //
        if ((highAlarmValue != UNDEFINED_TEMP) && (value > highAlarmValue)
                && zero.equals(getParamHighAlarmTime().getValue())) {
            getParamHighAlarmTime().setDirectValue(time);
        }
        //
        if ((lowAlarmValue != UNDEFINED_TEMP) && (value < lowAlarmValue)
                && zero.equals(getParamLowAlarmTime().getValue())) {
            getParamLowAlarmTime().setDirectValue(time);
        }
        //
        if ((highAlarmValue != UNDEFINED_TEMP) && (value > highAlarmValue)
                && zero.equals(getParamHighAlarmTime().getValue())) {
            getParamHighAlarmTime().setDirectValue(time);
        }
    }
    private void onStatusChange(Parameter parameter) {
        long time = System.currentTimeMillis();
        getParamResetTime().setDirectValue(new Long(time));
        parameter.setDirectValue(Boolean.FALSE);
    }
}
