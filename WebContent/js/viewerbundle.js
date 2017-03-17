/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmory imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmory exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		Object.defineProperty(exports, name, {
/******/ 			configurable: false,
/******/ 			enumerable: true,
/******/ 			get: getter
/******/ 		});
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 4);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports) {

module.exports = React;

/***/ },
/* 1 */
/***/ function(module, exports) {

"use strict";
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
/**
 * Bill of materials
 */
class Bom {
    constructor() {
        this.bom = new Map(); //part-id
    }
    /**
     * Add to BOM
     *
     * @param p The part
     * @param n Quantity
     */
    addPart(p, n) {
        if (n == 0)
            n = 1;
        if (this.bom.has(p))
            this.bom.set(p, this.bom.get(p) + n);
        else
            this.bom.set(p, n);
    }
    addBom(b, n) {
        if (n == 0)
            n = 1;
        for (let p of b.bom.keys()) {
            this.addPart(p, b.bom.get(p) * n);
        }
    }
    isEmpty() {
        return this.bom.size == 0;
    }
}
exports.Bom = Bom;
/**
 * One author
 */
class Author {
    /**
     * Copy (for parsing)
     */
    copyfrom(o) {
        Object.assign(this, o);
    }
}
exports.Author = Author;
/**
 * One brick
 */
class Brick {
    constructor() {
        this.mapFunctions = new Map();
    }
    /**
     * Get BOM as only what this particular brick contains
     */
    getBom(proj, recursive) {
        var bom = new Bom();
        this.functions.forEach(function (func) {
            func.implementations.forEach(function (imp) {
                if (imp.isPart()) {
                    var p = imp.getPart(proj);
                    //bom.addPart(p.id, +func.quantity);
                    bom.addPart(p.id, imp.quantity);
                }
                else if (imp.isBrick()) {
                    if (recursive) {
                        var b = imp.getBrick(proj).getBom(proj, true);
                        //bom.addBom(b,+func.quantity);                    
                        bom.addBom(b, imp.quantity);
                    }
                }
                else {
                    console.log("bad imp type" + imp.type);
                }
            });
        });
        return bom;
    }
    /**
     * Get bricks this brick has as direct children
     */
    getChildBricks() {
        var referenced = new Set();
        //this.mapFunctions.values().forEach(function(func:BrickFunction){
        for (let func of this.mapFunctions.values()) {
            func.implementations.forEach(function (imp) {
                if (imp.isBrick()) {
                    referenced.add(imp.id);
                }
            });
        }
        return referenced;
    }
    /**
     * Copy (for parsing)
     */
    copyfrom(o) {
        Object.assign(this, o);
        this.functions = [];
        var t = this;
        //Copy sub-bricks and functions
        o.functions.forEach(function (ofunc, index) {
            var f = new BrickFunction();
            f.copyfrom(ofunc);
            //t.functions.push(f);
            f.id = "" + index;
            t.mapFunctions.set("" + index, f);
        });
    }
}
exports.Brick = Brick;
/**
 * One function for a brick
 */
class BrickFunction {
    copyfrom(o) {
        Object.assign(this, o);
        this.implementations = [];
        o.implementations.forEach((oi, index) => {
            var f = new FunctionImplementation();
            f.copyfrom(oi);
            this.implementations.push(f);
        });
    }
}
exports.BrickFunction = BrickFunction;
class FunctionImplementation {
    isPart() {
        return this.type == "part";
    }
    isBrick() {
        return this.type == "brick";
    }
    getPart(proj) {
        return proj.getPartByName(this.id); //parts[+this.id];
    }
    getBrick(proj) {
        return proj.getBrickByName(this.id); //bricks[+this.id];
    }
    copyfrom(oi) {
        this.id = oi.id;
        this.quantity = oi.quantity;
        this.type = oi.type;
    }
}
exports.FunctionImplementation = FunctionImplementation;
/**
 * One associated file
 */
class MediaFile {
}
exports.MediaFile = MediaFile;
/**
 * One part
 */
class Part {
    copyfrom(o) {
        Object.assign(this, o);
    }
}
exports.Part = Part;
/**
 * One step-by-step instruction
 */
class StepByStepInstruction {
}
exports.StepByStepInstruction = StepByStepInstruction;
/**
 * One assembly step (or any instruction step)
 */
class AssemblyStep {
}
exports.AssemblyStep = AssemblyStep;
/**
 * reference - to be removed?
 */
class AssemblyStepComponent {
}
exports.AssemblyStepComponent = AssemblyStepComponent;
class BrickTree {
    constructor() {
        this.children = [];
    }
}
exports.BrickTree = BrickTree;
/**
 * One docubricks project
 */
class Project {
    constructor() {
        this.bricks = [];
        this.parts = [];
        this.authors = [];
        this.mapBricks = new Map();
        this.mapParts = new Map();
        this.mapAuthors = new Map();
    }
    getBrickByName(id) {
        var b = this.mapBricks.get(id);
        if (b === undefined) {
            console.error("---- no such brick \"" + id + "\"");
            for (let i of this.mapBricks.keys())
                console.error(i);
        }
        return b;
    }
    getPartByName(id) {
        return this.mapParts.get(id);
    }
    getAuthorById(id) {
        return this.mapAuthors.get(id);
    }
    /**
     * Get all the roots. Hopefully only one
     */
    getRootBricks() {
        //See what is referenced
        var referenced = new Set();
        for (let b of this.mapBricks.values()) {
            for (let c of b.getChildBricks())
                referenced.add(c);
        }
        //Pick unreferenced bricks as roots
        var roots = [];
        for (let b of this.mapBricks.values())
            if (!referenced.has(b.id))
                roots.push(b.id);
        //Backup: Pick anything as the root. Not great but better
        if (roots.length == 0)
            for (let b of this.mapBricks.values()) {
                roots.push(b.id);
                break;
            }
        return roots;
    }
    getBrickTree() {
        var thetree = [];
        //Pick unreferenced bricks as roots
        var roots = this.getRootBricks();
        var referenced = new Set();
        for (let b of this.mapBricks.values())
            if (!referenced.has(b.id))
                thetree.push(this.getBrickTreeR(this, b, referenced));
        return thetree;
    }
    getBrickTreeR(thisProject, thisbrick, referenced) {
        var t = new BrickTree();
        t.brick = thisbrick; //this.mapBricks.get(thisbrick);//bricks[+thisbrick];
        referenced.add(thisbrick.id);
        var children = thisbrick.getChildBricks();
        for (let c of children) {
            if (!referenced.has(c)) {
                t.children.push(thisProject.getBrickTreeR(thisProject, thisProject.getBrickByName(c), referenced));
            }
        }
        return t;
    }
    /**
     * For parsing only
     */
    copyfrom(o) {
        //Copy bricks
        for (let index in o.bricks) {
            var ob = o.bricks[index];
            var b = new Brick();
            b.copyfrom(ob);
            var si = "" + index;
            b.id = si;
            this.mapBricks.set(si, b);
        }
        ;
        //Copy parts
        for (let index in o.parts) {
            var op = o.parts[index];
            var p = new Part();
            p.copyfrom(op);
            p.id = "" + index;
            this.mapParts.set(index, p);
        }
        ;
        //Copy authors
        for (let index in o.authors) {
            var oa = o.authors[index];
            var a = new Author();
            a.copyfrom(oa);
            a.id = "" + index;
            this.mapAuthors.set(index, a);
        }
        ;
    }
    /**
     * Get the name of the project - use the name of the root brick
     */
    getNameOfProject() {
        var roots = this.getRootBricks();
        if (roots.length > 0) {
            var root = this.getBrickByName(roots[0]);
            return root.name;
        }
        else
            return "";
    }
}
exports.Project = Project;
function docubricksFromJSON(s) {
    var proj = JSON.parse(s);
    var realproj = new Project();
    realproj.copyfrom(proj);
    return realproj;
}
exports.docubricksFromJSON = docubricksFromJSON;


/***/ },
/* 2 */
/***/ function(module, exports, __webpack_require__) {

"use strict";
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(0);
class DocubricksProject extends React.Component {
    /**
     * Render the tree of bricks
     */
    renderBrickTree(t) {
        var mnodes = [];
        for (let n of t) {
            mnodes.push(this.renderBrickTreeR(n));
        }
        return React.createElement("ul", null, mnodes);
    }
    renderBrickTreeR(t) {
        var proj = this.props.proj;
        var mnodes = [];
        for (let c of t.children) {
            mnodes.push(React.createElement("ul", { key: "treechild_" + c.brick.id }, this.renderBrickTreeR(c)));
        }
        return React.createElement("li", { key: "treenode_" + t.brick.id },
            React.createElement("a", { href: "#brick_" + t.brick.id }, t.brick.name),
            React.createElement("ul", null, mnodes));
    }
    /**
     * Main rendering function
     */
    render() {
        var proj = this.props.proj;
        document.title = "DocuBricks - " + proj.getNameOfProject();
        var brickTree = proj.getBrickTree();
        console.log(brickTree);
        var itemsAuthors = [];
        for (let a of proj.mapAuthors.values()) {
            itemsAuthors.push(React.createElement(Author, { key: "author_" + a.id, proj: proj, authorid: a.id }));
        }
        var itemsBricks = [];
        for (let b of proj.mapBricks.values()) {
            itemsBricks.push(React.createElement("div", { key: b.id },
                " ",
                React.createElement(Brick, { proj: proj, brickid: b.id })));
        }
        var itemsParts = [];
        for (let b of proj.mapParts.values()) {
            itemsBricks.push(React.createElement("div", { key: b.id },
                " ",
                React.createElement(Part, { proj: proj, partid: b.id })));
        }
        var itemsTotalBom = [];
        var roots = proj.getRootBricks();
        if (roots.length > 0) {
            var root = proj.getBrickByName(roots[0]);
            var bom = root.getBom(proj, true);
            if (!bom.isEmpty()) {
                itemsTotalBom.push(React.createElement("div", null,
                    React.createElement("div", { className: "divbom" },
                        React.createElement("h1", { id: "bom" }, "Total bill of materials for this project")),
                    React.createElement(BomList, { proj: proj, bom: bom })));
            }
        }
        var projectid = getQueryStringValue("id");
        var downloadlink = "DownloadZip?id=" + projectid;
        return React.createElement("div", null,
            React.createElement("div", { className: "w3-sidebar" },
                React.createElement("h3", null,
                    React.createElement("a", { href: "./" }, "DocuBricks")),
                React.createElement("h3", null,
                    React.createElement("a", { href: downloadlink }, "Download project")),
                React.createElement("br", null),
                React.createElement("h3", null, "Bricks:"),
                this.renderBrickTree(brickTree),
                React.createElement("h3", null,
                    React.createElement("a", { href: "#bom" }, "Bill of materials")),
                React.createElement("h3", null,
                    React.createElement("a", { href: "#authors" }, "Authors"))),
            React.createElement("div", { className: "w3-container" },
                React.createElement("div", null, itemsBricks),
                React.createElement("div", null, itemsParts),
                React.createElement("div", { className: "brickdiv" },
                    React.createElement("h1", { id: "authors" }, "Authors")),
                React.createElement("table", null,
                    React.createElement("thead", null,
                        React.createElement("tr", null,
                            React.createElement("th", null, "Name"),
                            React.createElement("th", null, "E-mail"),
                            React.createElement("th", null, "Affiliation"),
                            React.createElement("th", null, "ORCID"))),
                    React.createElement("tbody", null, itemsAuthors)),
                itemsTotalBom));
    }
}
exports.DocubricksProject = DocubricksProject;
class Brick extends React.Component {
    render() {
        var proj = this.props.proj;
        var brickid = this.props.brickid;
        var brick = proj.getBrickByName(brickid);
        var brickkey = "brick" + this.props.brickid;
        const pStyle = {
            textAlign: "left" //text-align
        };
        var mnodes = [];
        var addField = function (name, value) {
            if (value != "")
                mnodes.push(React.createElement("p", { key: brickkey + "_" + name },
                    React.createElement("b", null,
                        name,
                        ": "),
                    value));
        };
        addField("Description", brick.long_description);
        mnodes.push(React.createElement("p", { key: brickkey + "_brickabstract", style: pStyle }, brick.abstract));
        mnodes.push(React.createElement(Files, { key: brickkey + "_files", proj: proj, files: brick.files, basekey: brickkey }));
        addField("License", brick.license);
        addField("Notes", brick.notes);
        //Authors
        if (brick.authors.length != 0) {
            var alist = "";
            for (let a of brick.instructions) {
                if (alist.length != 0) {
                    alist = alist + ", " + a.name;
                }
                else
                    alist = a.name;
            }
            addField("Authors", brick.notes);
        }
        //Functions & implementations
        var reqnodes = [];
        for (let func of brick.mapFunctions.values()) {
            var fnodes = [];
            console.log(666);
            console.log(fnodes);
            for (let imp of func.implementations) {
                var impend = "";
                if (fnodes.length != 0)
                    fnodes.push(React.createElement("b", null, ", "));
                if (imp.isPart()) {
                    var ip = imp.getPart(proj);
                    fnodes.push(React.createElement("a", { href: "#part_" + imp.id },
                        ip.name,
                        " ",
                        React.createElement("b", null,
                            "x ",
                            imp.quantity)));
                }
                else if (imp.isBrick()) {
                    var ib = imp.getBrick(proj);
                    fnodes.push(React.createElement("a", { href: "#brick_" + imp.id },
                        ib.name,
                        " ",
                        React.createElement("b", null,
                            "x ",
                            imp.quantity)));
                }
            }
            var desc = "";
            if (func.description != "")
                desc = func.description + ": ";
            reqnodes.push(React.createElement("li", null,
                React.createElement("b", null, desc),
                fnodes));
            console.log(fnodes);
        }
        var reqnodes2 = [];
        if (reqnodes.length != 0) {
            reqnodes2 = [React.createElement("div", null,
                    React.createElement("b", null, "Requires:"),
                    React.createElement("ul", null, reqnodes))];
        }
        //The bill of materials
        /*
        var bom:Docubricks.Bom = brick.getBom(proj,false);
        if(!bom.isEmpty()){
            mnodes.push(
                    <div>
                        <div className="divbrickbom">
                            <h3>Materials for this brick</h3>
                        </div>
                        <BomList proj={proj} bom={bom}/>
                    </div>);
        }
        */
        //All the instructions
        var instrnodes = [];
        for (let instr of brick.instructions) {
            instrnodes.push(React.createElement("div", { key: brickkey + "_" + instr.name },
                React.createElement(InstructionList, { proj: proj, brick: brick, part: null, instr: instr })));
        }
        var ret = React.createElement("div", null,
            React.createElement("div", { className: "brickdiv" },
                React.createElement("h1", { id: "brick_" + brickid }, brick.name)),
            mnodes,
            reqnodes2,
            instrnodes);
        return ret;
    }
}
exports.Brick = Brick;
class Part extends React.Component {
    render() {
        var proj = this.props.proj;
        var partid = this.props.partid;
        var part = proj.getPartByName(partid);
        var partkey = "part" + partid;
        ////////////////////////////xpublic files: MediaFile[];
        var mnodes = [];
        var addField = function (name, value) {
            if (value != "")
                mnodes.push(React.createElement("p", { key: partkey + "_" + name },
                    React.createElement("b", null,
                        name,
                        ": "),
                    value));
        };
        addField("Description", part.description);
        mnodes.push(React.createElement(Files, { key: partkey + "_files", proj: proj, files: part.files, basekey: partkey }));
        addField("Supplier", part.supplier);
        addField("Supplier catalog #", part.supplier_part_num);
        addField("Manufacturer catalog #", part.manufacturer_part_num);
        if (part.url != "")
            mnodes.push(React.createElement("p", { key: partkey + "_url" },
                React.createElement("b", null, "URL: "),
                formatURL(part.url)));
        if (part.material_amount != "")
            addField("Material usage", part.material_amount + " " + part.material_unit);
        //All the instructions
        if (part.manufacturing_instruction.steps.length != null) {
            mnodes.push(React.createElement("div", { key: partkey + "_instr" },
                React.createElement(InstructionList, { proj: proj, brick: null, part: part, instr: part.manufacturing_instruction })));
        }
        var ret = React.createElement("div", null,
            React.createElement("div", { className: "partdiv" },
                React.createElement("h1", { id: "part_" + partid },
                    "Part: ",
                    part.name)),
            mnodes);
        return ret;
    }
}
exports.Part = Part;
class Author extends React.Component {
    render() {
        var proj = this.props.proj;
        var author = proj.getAuthorById(this.props.authorid);
        return React.createElement("tr", { key: "authorrow_" + author.id },
            React.createElement("td", null, author.name),
            React.createElement("td", null, author.email),
            React.createElement("td", null, author.affiliation),
            React.createElement("td", null, author.orcid));
    }
}
exports.Author = Author;
class InstructionList extends React.Component {
    render() {
        var proj = this.props.proj;
        var brick = this.props.brick;
        var instr = this.props.instr;
        var key;
        if (brick != null)
            key = "instrBrick" + brick.id + "_instr" + instr.name;
        else
            key = "instrPart" + this.props.part.id + "_instr" + instr.name;
        var snodes = [];
        var curstep = 1;
        for (let step of instr.steps) {
            var stepkey = key + "_" + curstep;
            snodes.push(React.createElement("div", { key: stepkey },
                React.createElement("nav", null,
                    React.createElement(Files, { proj: proj, files: step.files, basekey: stepkey })),
                React.createElement("article", null,
                    React.createElement("b", null,
                        "Step ",
                        curstep,
                        ". "),
                    step.description)));
            const divclear = { clear: "both" };
            snodes.push(React.createElement("div", { key: stepkey + "_end", style: divclear }));
            curstep++;
        }
        if (snodes.length > 0)
            return React.createElement("div", { key: key + "_main" },
                React.createElement("h3", null,
                    "Instruction: ",
                    instr.name),
                snodes);
        else
            return React.createElement("div", { key: key + "_main" });
    }
}
exports.InstructionList = InstructionList;
class BomList extends React.Component {
    render() {
        var proj = this.props.proj;
        var snodes = [];
        var roots = proj.getRootBricks();
        if (roots.length > 0) {
            var root = proj.getBrickByName(roots[0]);
            var bom = root.getBom(proj, true);
            //Loop over parts
            var key = "mainbom_";
            var curstep = 1;
            for (let partid of bom.bom.keys()) {
                var quantity = bom.bom.get(partid);
                var part = proj.getPartByName(partid);
                var stepkey = key + curstep;
                curstep++;
                snodes.push(React.createElement("tr", { key: stepkey },
                    React.createElement("td", null, part.name),
                    React.createElement("td", null, quantity),
                    React.createElement("td", null, part.supplier),
                    React.createElement("td", null, part.supplier_part_num),
                    React.createElement("td", null, formatURL(part.url))));
            }
        }
        else {
            return React.createElement("div", null);
        }
        return React.createElement("div", { key: key + "_main" },
            React.createElement("table", null,
                React.createElement("thead", null,
                    React.createElement("tr", null,
                        React.createElement("th", null, "Part"),
                        React.createElement("th", null, "Quantity"),
                        React.createElement("th", null, "Supplier"),
                        React.createElement("th", null, "Supplier part number"),
                        React.createElement("th", null, "URL"))),
                React.createElement("tbody", null, snodes)));
    }
}
exports.BomList = BomList;
var urlcount = 1;
var formatURL = function (url) {
    urlcount = urlcount + 1;
    var ret = [];
    if (url != "") {
        var s = new String(url);
        s = s.replace(/.+\:\/\//gi, "");
        s = s.replace(/\/.*/gi, "");
        ret.push(React.createElement("a", { key: "url_" + urlcount + "_" + url, href: url }, s.toString()));
    }
    return ret;
};
var formatURLfile = function (url, filename) {
    urlcount = urlcount + 1;
    var ret = [];
    if (url != "") {
        ret.push(React.createElement("p", { key: "url_" + urlcount + "_" + url },
            React.createElement("a", { href: url },
                React.createElement("b", null,
                    "File: ",
                    filename))));
    }
    return ret;
};
var getQueryStringValue = function (key) {
    return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).
        replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
};
class Files extends React.Component {
    render() {
        var proj = this.props.proj;
        var files = this.props.files;
        function isImage(url) {
            return (url.toLowerCase().match(/\.(jpeg|jpg|gif|png|svg)$/) != null);
        }
        var projectid = getQueryStringValue("id");
        var basedir = "./project/";
        if (projectid != "") {
            basedir = "./project/" + projectid + "/";
        }
        //var downloadlink="DownloadZip?id="+projectid;
        //Collect the files and images
        var inodes = [];
        var fnodes = [];
        for (let f of files) {
            const imgStyle = {
                maxWidth: '300px',
                //width:'100%',
                maxHeight: '300px',
                margin: '5px'
            };
            var imgurl = basedir + f.url;
            if (isImage(imgurl)) {
                inodes.push(React.createElement("a", { key: this.props.basekey + f.url, href: imgurl, "data-lightbox": "image" },
                    React.createElement("img", { src: imgurl, style: imgStyle })));
            }
            else {
                fnodes.push(formatURLfile(imgurl, f.url)[0]);
            }
        }
        return React.createElement("div", null,
            fnodes,
            inodes);
    }
}
exports.Files = Files;


/***/ },
/* 3 */
/***/ function(module, exports) {

module.exports = ReactDOM;

/***/ },
/* 4 */
/***/ function(module, exports, __webpack_require__) {

"use strict";
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(0);
const ReactDOM = __webpack_require__(3);
const docubricksViewer_1 = __webpack_require__(2);
const Docubricks = __webpack_require__(1);
//alert(getQueryStringValue("id")); 
var s = document.getElementById("hiddendata").textContent;
document.getElementById("hiddendata").textContent = "";
var docu = Docubricks.docubricksFromJSON(s);
ReactDOM.render(React.createElement(docubricksViewer_1.DocubricksProject, { proj: docu }), document.getElementById("example"));


/***/ }
/******/ ]);
//# sourceMappingURL=viewerbundle.js.map