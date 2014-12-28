package com.sb.elsinore.html;

import static org.rendersnake.HtmlAttributesFactory.class_;
import static org.rendersnake.HtmlAttributesFactory.id;
import static org.rendersnake.HtmlAttributesFactory.type;
import static org.rendersnake.HtmlAttributesFactory.value;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import com.sb.elsinore.LaunchControl;
import com.sb.elsinore.Messages;
import com.sb.elsinore.inputs.PhSensor;

public class PhSensorForm implements Renderable {

    private final PhSensor phSensor;

    public PhSensorForm(PhSensor newSensor) {
        this.phSensor = newSensor;
    }

    @Override
    public void renderOn(final HtmlCanvas html) throws IOException {
        html.div(id(phSensor.getName() + "-editPhSensor").class_("col-md-12"))
            .form(id(phSensor.getName() + "-editPhSensor")
                    .name(phSensor.getName() + "-edit"))
                .input(type("text").class_("form-control")
                        .name("name").id("name")
                        .value(phSensor.getName()))
                .br();
                html.input(type("text").class_("form-control")
                        .name("adc_pin")
                        .id("adc_pin").value(phSensor.getAIN())
                        .add("placeholder", Messages.ANALOGUE_PIN))
                .br()
                // Replace this with a list
                .select(class_("holo-spinner").name("dsAddress")
                        .id("dsAddress"));
                html.option(value("").selected_if(
                        "".equals(phSensor.getDsAddress())))
                        .write(Messages.DS2450_ADDRESS)
                ._option();
                for (String addr: LaunchControl.getOneWireDevices("/20")) {
                    String address = addr.substring(1);
                    html.option(value(address)
                        .selected_if(address.equals(phSensor.getDsAddress())))
                        .write(address)
                    ._option();
                }

                html._select();
                html.input(type("text").class_("form-control")
                        .name("dsOffset")
                        .id("dsOffset").value(phSensor.getDsOffset())
                        .add("placeholder", Messages.DS2450_OFFSET))
                .br();
                html.select(class_("holo-spinner").name("ph_model")
                        .id("ph_model"));
                html.option(value("").selected_if(
                        "".equals(phSensor.getDsAddress())))
                    .write(Messages.PH_MODEL)
                ._option();
                for (String model: phSensor.getAvailableTypes()) {
                    html.option(value(model)
                        .selected_if(model.equals(phSensor.getModel())))
                        .write(model)
                    ._option();
                }

                html._select();
                html.input(type("number").class_("form-control")
                        .add("step", "any")
                        .name("calibration")
                        .id("calibration").value("")
                        .add("placeholder", Messages.CALIBRATE))
                .br();
                html.button(id("updatePhSensor-" + phSensor.getName())
                        .onClick("submitForm(this.form);")
                        .class_("btn"))
                    .write(Messages.UPDATE)
                ._button()
                .button(id("cancelPh-" + phSensor.getName())
                        .onClick("cancelPhEdit(" + phSensor.getName() + ");")
                        .class_("btn"))
                    .write(Messages.CANCEL)
                ._button()
            ._form()
        ._div();
    }

}