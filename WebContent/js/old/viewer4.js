var Author = (function () {
    function Author() {
    }
    return Author;
})();
var Brick = (function () {
    function Brick() {
    }
    return Brick;
})();
var DocubricksProject = (function () {
    function DocubricksProject() {
    }
    return DocubricksProject;
})();
var BrickFunction = (function () {
    function BrickFunction() {
    }
    return BrickFunction;
})();
var FunctionImplementation = (function () {
    function FunctionImplementation() {
    }
    return FunctionImplementation;
})();
var MediaFile = (function () {
    function MediaFile() {
    }
    return MediaFile;
})();
var PhysicalPart = (function () {
    function PhysicalPart() {
    }
    return PhysicalPart;
})();
var StepByStepInstruction = (function () {
    function StepByStepInstruction() {
    }
    return StepByStepInstruction;
})();
var AssemblyStep = (function () {
    function AssemblyStep() {
    }
    return AssemblyStep;
})();
var AssemblyStepComponent = (function () {
    function AssemblyStepComponent() {
    }
    return AssemblyStepComponent;
})();
//json2ts.com
function loadxmlJSON() {
    var xmls = document.getElementById("hiddendata");
    var s = xmls.innerHTML;
    var ob = JSON.parse(s);
    console.log(ob);
    populatePage(ob);
}
function populatePage(s) {
}
//# sourceMappingURL=viewer4.js.map