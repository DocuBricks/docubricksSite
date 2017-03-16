

class Author {
    public name: string;
    public email: string;
    public orcid: string;
    public affiliation: string;
}


class Brick {
    public name: string;
    public abstract: string;
    public long_description: string;
    public notes: string;
    public license: string;
    public files: MediaFile[];
    public authors: Author[];
    public functions: BrickFunction[];
    public instructions: StepByStepInstruction[];
}


class DocubricksProject {
    public something: string;

    /*
        render: function() {
            return <div>Hello {this.someth } </div>;
        }*/
}


class BrickFunction {
    public id: string;
    public description: string;
    public designator: string;
    public quantity: string;
    public implementations: FunctionImplementation[];
}


class FunctionImplementation {
    public type: Brick;
    public id: Brick;
}



class MediaFile {
    public url: string;
}


class PhysicalPart {
    public name: string;
    public description: string;

    public supplier: string;
    public supplier_part_num: string;
    public manufacturer_part_num: string;
    public url: string;

    public material_amount: string;
    public material_unit: string;

    public files: MediaFile[];

    public manufacturing_instruction: StepByStepInstruction;
}


class StepByStepInstruction {
    public name: string;
    public steps: AssemblyStep[];
}


class AssemblyStep {
    public description: string;
    public files: MediaFile[];
    public components: AssemblyStepComponent[];
}


class AssemblyStepComponent {
    public quantity: string;
    public id: string;
}


//json2ts.com

function loadxmlJSON() {

    var xmls = document.getElementById("hiddendata");
    var s: string = xmls.innerHTML;
    var ob = JSON.parse(s);
    console.log(ob);
    populatePage(ob);
}



function populatePage(s) {



}

