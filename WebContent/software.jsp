<jsp:include page="header.html" />
<jsp:include page="top.jsp" />

<div id="main">
	<div class="container">
		<div class="row page-max-width">
			<p class="lead">
				<h3>Download the Editor</h3></p>
			<p>
				Our (offline) editor software helps you create a good documentation of your
				project that complies with the DocuBricks XML scheme (below), as required
				for uploads to this online repository.
				The editor enables you document to a project in a modular fashion along
				it's functionality, with all files, media and details
				linked into place. <br>
				Our modular documentations are well suited for open source hardware projects, enabling
				the communication of design decisions with advice on assembly, testing and usage.
				They differ from simpler linear assembly-only documentations as
				used in commercial home-assembly kits or repair guides, because those do
				not aim for the project to be modifiable by others.</p>
			<p>
				Please download our free and open source (java)
				editor from the repository behind this link:</p>
			<p class="lead">
				 <font color="#d40000">GET STARTED HERE:</font> <a href="http://www.endrov.net/temp/">DocuBricks Editor downloads</a>
			</p>
			<p>
				No installation is needed for this software, but it is written in Java
				and you might have to update your java version for this software to work correctly.
				The editor will save your project	into a XML document and a folder with
				your files (images, videos, CAD, etc.), which you can save on your PC
				or host for example on GitHub to re-open, modify or fork later. Once you
				are ready to submit the project to this repository as draft or release version,
				please log-in and upload the files as one zip-folder.</p>

			<ul>
					<p><strong>Tips and tricks for the editor use:</strong></p>
					<li>Drag-and-drop media and design files to the desired location in the documentation.</li>
					<li>Write "todo" into a field to highlight it for later attention.</li>
					<li>Use DocuBricks to create
						a modular documentation by describing parts of your hardware,
						 wetware, or code in their own "bricks". We recommend to create a general
					 "top-level" brick that describes the general aim of the project and then
					 referring to "sub-bricks" by naming them as part of the content-section
					 of the top-brick or other sub-bricks.</li>
					<li>Group parts in the content of a brick and give them a name that
						reflects their functionality. In this way, you can indicate
						to the users for what reason you included these set of screws, nuts, or other parts.</li>
					<li>Add manufacturing instructions to parts, if producing or finishing
						this part to be ready for use requires attention.</li>
					<li>Add custom instructions to a brick to detail important aspects of the
					project such as calibration instructions, testing, usage procedures, safety considerations,
				educational ideas, or further suggestions to improve the project.</li>
					<li>Save your project to a new folder location in order to copy all
						associated files into a new directory.</li>
			</ul>


			<h3>Source code</h3>

			<p>All our software is proudly free and open source. At our GitHub repository,
				you can find the source code of the java editor, the type-script (java-script)
				and HTML viewer used by this website to render the XML-based documentations
				nicely, and the code for the website itself.</p>
			<p class="lead">
				<a href="https://github.com/mahogny/docubricksEditor">DocuBricks source code repository</a>
			</p>

			<p>Alternatively, you can access the code in the directory of our lead-developer: <br>
				<a href="https://github.com/mahogny/docubricksEditor">DocuBricks editor</a>;
				<a href="https://github.com/mahogny/docubricksViewer">DocuBricks viewer</a>;
				<a href="https://github.com/mahogny/docubricksSite">DocuBricks site</a>
			</p>


<h3>Introducing the DocuBricks XML format</h3>

<p>The DocuBricks format is a modular documentation structure for high-quality presentation of open hardware projects. We feel that the available tools were not sufficient to enable sharing useful open source hardware projects that integrate different types of components. These projects should be documented in a modular fashion where it is easy to assess how the project solves a problem, whether the information is complete and if calibration strategies etc. are given.</p>
<p>We are still extending the format according to the needs of the users. In the future, we intend to develop the format into a more formal standard.</p>

<h4>Basic structure</h4>

<p>A DocuBricks documentation of a hardware <strong>project </strong>is characterised by a hierarchy of modular documentation bricks. A <strong>brick</strong> is a standalone piece of documentation describing a functional part of the overall project and can contain more specialised bricks lower in the hierarchy.</p>

<div style="box-shadow: 5px 5px 4px grey; background-color: lightgrey;">
	<p><strong>Modular projects using bricks</strong></p>
	<p>
	A documentation brick is nothing more or less than a standalone part of the documentation as freely defined by the author. One could write the entire documentation of a project into one brick, but creating <strong>dependent bricks</strong> to the &ldquo;<strong>project overview brick</strong>&rdquo;, helps disentangling the project to make it easier to document, understand and evolve. The following questions might help you identifying appropriate parts of your project that could be documented as a brick: What parts of the hardware could be used as a component of another project or a variation of this hardware? What solutions do you find most noteworthy given your experiences during the design iterations? What separate functions or actions does the hardware perform?</p>
</div>

<ul>
		<p>Every brick contains the following documentation sections:</p>
		<li>Brick overview (description)</li>
		<li>Copyright (License and author; inherited from higher bricks if not specified)</li>
		<li>Components (<strong>Function</strong>s of the brick&rsquo;s hardware and their implementations. <strong>Implementation</strong>s are references to other more specialised bricks or parts from the &ldquo;bill of materials&rdquo;; bottom level bricks can only contain references to parts)</li>
		<li>Assembly instructions (stepwise instructions with associated media files)</li>
		<li>Custom instructions (such as calibration, testing, safety, user guide, teaching, etc.)</li>
</ul>

<p>A <strong>part</strong> is a resource listed in the <strong>bill of materials (BOM)</strong>, which is needed to assemble or run the project, mainly hardware parts e.g. a screw, digital design files representing a single object e.g. an STL file for 3d printing, specialist tools, or operating software. Each part is defined in the BOM and can contain supplier information, media and their own manufacturing instructions e.g. when the object is made from a digital file or needs simple pre-processing before it is assembled with other parts.
	<br>
<strong>Modifiable design files</strong> are a key element of open sourcing a hardware project, and are added as media files to DocuBricks documentations. The location for design files can be chosen flexibly depending on the level of integration of the design. They are either saved in the respective part, the respective brick, or even in a dedicated design brick, which details aspects of integration and customisation.</p>

<div style="box-shadow: 5px 5px 4px grey; background-color: lightgrey;">
	<p><strong>File storage</strong></p>
	<p>
	All files that are referred to in the DocuBricks XML document should be stored in an accessible folder close to the document. When the documentation is created with the <strong>DocuBricks editor software</strong> available at docubricks.com, such a folder called &ldquo;usdata&rdquo; is automatically created with all the files that are dragged-and-dropped into the editor.</p>
</div>

<h3>Definitions </h3>

The following lists define the elements of the DocuBricks XML format:</p>

<p>&lt;<strong>docubricks</strong>&gt; <em>tag for the start of DocuBricks documentation of the project; the brackets are not written in the following definitions</em></p>

<p><strong>author id</strong>(string)<strong>:</strong></p>
<ul>
	<li><strong> name </strong>(string)</li>
	<li><strong> email </strong>(string)</li>
	<li><strong> orcid </strong>(string)</li>
	<li><strong> affiliation </strong>(string)</li>
</ul>

<p><strong>part id</strong>(string)<strong>:</strong></p>
<ul>
	<li><strong> name </strong>(string)</li>
	<li><strong> description </strong>(string)</li>
	<li><strong> supplier </strong>(string)</li>
	<li><strong> supplier_part_num </strong>(string)</li>
	<li><strong> manufacturer_part_num </strong>(string)</li>
	<li><strong> url </strong>(string) <em>link to an internet source of the part</em></li>
	<li><strong> material_amount </strong>(string)</li>
	<li><strong> material_unit </strong>(string)</li>
	<li><strong> media: file(s)</strong> (url) <em>images, videos, CAD files, and more</em></li>
	<li><strong> manufacturing_instruction: step(s): </strong><em>step by step instructions</em></li>
<ul>
	<li><strong>description </strong>(string)</li>
	<li><strong>media: file(s)</strong> (url)</li>
</ul>
</ul>

<p><strong>brick id</strong>(string)<strong>:</strong></p>
<ul>
	<li><strong> name </strong>(string)</li>
	<li><strong> abstract </strong>(string)</li>
	<li><strong> long_description </strong>(string)</li>
	<li><strong> notes </strong>(string)</li>
	<li><strong> media:</strong> <strong>file </strong>(url)</li>
	<li><strong> license </strong>(string)</li>
	<li><strong> author(s)</strong> (<strong>id</strong>(string))</li>
	<li><strong> function(s) </strong>(<strong>id</strong>(string)):</li>
	<ul>
		<li><strong>description </strong>(string) <em>name of the function</em></li>
		<li><strong>implementation(s) </strong>(<strong>type</strong>(&ldquo;brick&rdquo; or &ldquo;part&rdquo;), <strong>quantity</strong> (int) <em>nr. needed</em><em> pieces, </em><em><strong>id</strong></em><em>(string) of brick or part respectively</em></li>
	</ul>
	<li><strong> assembly_instruction: step(s): </strong><em>step by step instructions</em></li>
	<ul>
		<li><strong>description </strong>(string)</li>
		<li><strong>media: file(s)</strong> (url)</li>
		<li><strong>component(s) </strong>(<strong>quantity</strong>(int), <strong>id</strong>(string) <em>of function)</em> <em>local reference to functions in the brick needed as component in this assembly step</em></li>
	</ul>
	<li><strong> instruction(s) </strong><strong>name</strong>(string)<strong>: step(s): </strong><em>other step by step instructions of custom type e.g. </em><em>safety, testing, calibration, user_manual, improvement_suggestions, etc.</em></li>
	<ul>
		<li><strong>description </strong>(string)</li>
		<li><strong>media: file(s)</strong> (url)</li>
		<li><strong>component(s) </strong>(<strong>quantity</strong>(int), <strong>id</strong>(string) <em>of function)</em> <em>local reference to functions in the brick needed as component in this assembly step</em></li>
</ul>
</ul>
<p><strong>&lt;/ docubricks &gt;</strong></li>
</div>
</div>
</div>

<jsp:include page="bottom.html" />
