/*jslint node: true */
var gulp = require('gulp'),
    packagejson = require('./package.json'),
    plug = require('gulp-load-plugins')(),
    rimraf = require('rimraf'),
    streamqueue = require('streamqueue'),
    stylish = require('jshint-stylish');


var htmlminOptions = {
    collapseBooleanAttributes: true,
    collapseWhitespace: true,
    removeAttributeQuotes: true,
    removeComments: true,
    removeEmptyAttributes: true,
    // removeRedundantAttributes: true,
    removeScriptTypeAttributes: true,
    removeStyleLinkTypeAttributes: true
};

gulp.task('clean', function() {
    rimraf.sync('dist');
});

gulp.task('css', ['clean'], function() {
    return gulp.src('app.less')
        .pipe(plug.less())
        .pipe(plug.cssmin({
            keepSpecialComments: 0
        }))
        .pipe(plug.rename('app.full.min.css'))
        .pipe(gulp.dest('dist/'));
});

gulp.task('js', ['clean'], function() {

    var templateStream = gulp.src(['!node_modules/**', '!index.html',
            '!_SpecRunner.html', '!.grunt/**',
            '!dist/**', '!bower_components/**',
            '**/*.html'
        ])
        .pipe(plug.htmlmin(htmlminOptions))
        .pipe(plug.ngHtml2js({
            moduleName: packagejson.name
        }));

    var jsStream = plug.domSrc({
        file: 'index.html',
        selector: 'script[data-build!="exclude"]',
        attribute: 'src'
    });

    var combined = streamqueue({
        objectMode: true
    });

    combined.queue(jsStream);
    combined.queue(templateStream);

    return combined.done()
        .pipe(plug.concat('app.full.min.js'))
        .pipe(plug.ngmin())
        .pipe(plug.uglify())
        .pipe(gulp.dest('dist/'));
});

gulp.task('indexHtml', ['clean'], function() {
    return gulp.src('index.html')
        .pipe(plug.cheerio(function($) {
            $('script[data-remove!="exclude"]').remove();
            $('link').remove();
            $('body').append('<script src="app.full.min.js"></script>');
            $('head').append('<link rel="stylesheet" href="app.full.min.css">');
        }))
        .pipe(plug.htmlmin(htmlminOptions))
        .pipe(gulp.dest('dist/'));
});

gulp.task('images', ['clean'], function() {
    return gulp.src('img/**')
        .pipe(plug.imagemin())
        .pipe(gulp.dest('dist/'));
});

gulp.task('fonts', ['clean'], function() {
    return gulp.src('bower_components/font-awesome/fonts/**')
        .pipe(gulp.dest('dist/bower_components/font-awesome/fonts/'));
});

gulp.task('jshint', function() {
    gulp.src(['!node_modules/**', '!.grunt/**', '!dist/**', '!bower_components/**', '**/*.js'])
        .pipe(plug.jshint())
        .pipe(plug.jshint.reporter(stylish));
});

gulp.task('jscs', function() {
    gulp.src(['!node_modules/**', '!.grunt/**', '!dist/**', '!bower_components/**', '**/*.js'])
        .pipe(plug.jscs({
            fix: true
        }))
        .pipe(plug.jscs.reporter())
        .pipe(plug.jscs.reporter('fail'));
});

gulp.task('beautify', function() {
    gulp.src(['!node_modules/**', '!.grunt/**', '!dist/**', '!bower_components/**', '**/*.js'], {
            base: "./"
        })
        .pipe(plug.jsbeautifier({
            mode: 'VERIFY_AND_WRITE'
        }))
        .pipe(gulp.dest("./"));
});

gulp.task('test', function() {

    var templateStream = gulp.src(['bower_components/angular-mocks/angular-mocks.js', '**/*-spec.js',
        '!bower_components/**/*-spec.js', '!node_modules/**/*-spec.js'
    ]);
    var jsStream = plug.domSrc({
        file: 'index.html',
        selector: 'script[data-build!="exclude"]',
        attribute: 'src'
    });
    var combined = streamqueue({
        objectMode: true
    });

    combined.queue(jsStream);
    combined.queue(templateStream);

    return combined.done()
        .pipe(plug.jasminePhantom({
            integration: true
        }));
});

gulp.task('build', ['clean', 'css', 'js', 'indexHtml', 'images', 'fonts']);
gulp.task('format-js', ['beautify', 'jscs', 'jshint']);
