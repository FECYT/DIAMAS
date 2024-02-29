import {Spec} from "vega";

export const vegaAraña: Spec ={
  "background": "white",
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "title": {
    "text": "",
    "dy": -50,
    "anchor": "start",
    "encode": {"title": {"enter": {"fill": {"value": "#72777a"}}}}
  },
  "width": 400,
  "height": 400,
  "padding": 5,

  "signals": [
    {"name": "radius", "update": "width / 1.5"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "mousemove", "update": "datum"},
        {"events": "mouseout", "update": "{}"}
      ]
    },
    {
      "name": "Preguntas",
      "value": "all",
      "bind" : {
        "input": "select",
        "options": ["all", "Avanzado", "Básico"],
        "labels": ["Todas", "Avanzado", "Básico"]
      }
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Funding", "value": 30, "type": "Avanzado"},
        {"category": "Governance", "value": 22, "type": "Avanzado"},
        {"category": "Open Science", "value": 14, "type": "Avanzado"},
        {"category": "Edition", "value": 38, "type": "Avanzado"},
        {"category": "Technical", "value": 23, "type": "Avanzado"},
        {"category": "Visibility", "value": 23, "type": "Avanzado"},
        {"category": "Edib", "value": 23, "type": "Avanzado"},
        {"category": "Funding", "value": 30, "type": "Básico"},
        {"category": "Governance", "value": 22, "type": "Básico"},
        {"category": "Open Science", "value": 14, "type": "Básico"},
        {"category": "Edition", "value": 38, "type": "Básico"},
        {"category": "Technical", "value": 23, "type": "Básico"},
        {"category": "Visibility", "value": 23, "type": "Básico"},
        {"category": "Edib", "value": 23, "type": "Básico"}
      ],
      "transform": [
        {
          "type": "filter",
          "expr": "datum.type === Preguntas || Preguntas === 'all'"
        }
      ]
    },
    {
      "name": "keys",
      "source": "table",
      "transform": [
        {
          "type": "aggregate",
          "groupby": ["category"]
        }
      ]
    }
  ],

  "scales": [
    {
      "name": "angular",
      "type": "point",
      "range": {"signal": "[-PI, PI]"},
      "padding": 0.5,
      "domain": {"data": "table", "field": "category"}
    },
    {
      "name": "radial",
      "type": "linear",
      "range": {"signal": "[0, radius]"},
      "zero": true,
      "nice": false,
      // "domain": {"data": "table", "field": "value"},
      "domain": [0, 100],
      "domainMin": 0
    },
    {
      "name": "color",
      "type": "ordinal",
      "domain": ["Avanzado", "Básico"],
      "range": ["#1f77b4", "#ff7f0e"]
    }
  ],
  "legends": [
    {
      "title": "Tipo de Preguntas",
      "fill": "color",
      "orient": "none",
      "titleFontSize": 12,
      "labelFontSize": 10,
      "legendX": 350,
      "legendY": -280
    }
  ],


  "marks": [
    {
      "type": "group",
      "name": "categories",
      "zindex": 1,
      "from": {
        "facet": {"data": "table", "name": "facet", "groupby": ["type"]}
      },

      "marks": [
        {
          "type": "line",
          "name": "category-line",
          "from": {"data": "facet"},
          "encode": {
            "enter": {
              "interpolate": {"value": "linear-closed"},
              "x": {"signal": "scale('radial', datum.value) * cos(scale('angular', datum.category))"},
              "y": {"signal": "scale('radial', datum.value) * sin(scale('angular', datum.category))"},
              "stroke": {"scale": "color", "field": "type"},
              "strokeWidth": {"value": 1},
              "fill": {"scale": "color", "field": "type"},
              "fillOpacity": {"value": 0.1}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.value, 'Category': datum.category}"}
            }
          }
        },
        {
          "type": "text",
          "name": "value-text",
          "from": {"data": "category-line"},
          "encode": {
            "enter": {
              "x": {"signal": "datum.x"},
              "y": {"signal": "datum.y"},
              "text": {"signal": "datum.datum.value"},
              "align": {"value": "center"},
              "baseline": {"value": "middle"},
              "fill": {"value": "black"}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.datum.value, 'Category': datum.datum.category}"}
            }
          }
        }
      ]
    },
    {
      "type": "rule",
      "name": "radial-grid",
      "from": {"data": "keys"},
      "zindex": 0,
      "encode": {
        "enter": {
          "x": {"value": 0},
          "y": {"value": 0},
          "x2": {"signal": "radius * cos(scale('angular', datum.category))"},
          "y2": {"signal": "radius * sin(scale('angular', datum.category))"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    },
    {
      "type": "text",
      "name": "key-label",
      "from": {"data": "keys"},
      "zindex": 1,
      "encode": {
        "enter": {
          "x": {"signal": "(radius + 5) * cos(scale('angular', datum.category))"},
          "y": {"signal": "(radius + 5) * sin(scale('angular', datum.category))"},
          "text": {"field": "category"},
          "align": [
            {
              "test": "abs(scale('angular', datum.category)) > PI / 2",
              "value": "right"
            },
            {
              "value": "left"
            }
          ],
          "baseline": [
            {
              "test": "scale('angular', datum.category) > 0", "value": "top"
            },
            {
              "test": "scale('angular', datum.category) == 0", "value": "middle"
            },
            {
              "value": "bottom"
            }
          ],
          "fill": {"value": "black"},
          "fontWeight": {"value": "bold"}
        }
      }
    },
    {
      "type": "line",
      "name": "outer-line",
      "from": {"data": "radial-grid"},
      "encode": {
        "enter": {
          "interpolate": {"value": "linear-closed"},
          "x": {"field": "x2"},
          "y": {"field": "y2"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    }
  ]
}

export const vegaArañaEN: Spec ={
  "background": "white",
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "title": {
    "text": "",
    "dy": -50,
    "anchor": "start",
    "encode": {"title": {"enter": {"fill": {"value": "#72777a"}}}}
  },
  "width": 400,
  "height": 400,
  "padding": 5,

  "signals": [
    {"name": "radius", "update": "width / 1.5"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "mousemove", "update": "datum"},
        {"events": "mouseout", "update": "{}"}
      ]
    },
    {
      "name": "Questions",
      "value": "all",
      "bind" : {
        "input": "select",
        "options": ["all", "Advanced", "Basic"],
        "labels": ["All", "Advanced", "Basic"]
      }
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Funding", "value": 30, "type": "Advanced"},
        {"category": "Governance", "value": 22, "type": "Advanced"},
        {"category": "Open Science", "value": 14, "type": "Advanced"},
        {"category": "Edition", "value": 38, "type": "Advanced"},
        {"category": "Technical", "value": 23, "type": "Advanced"},
        {"category": "Visibility", "value": 23, "type": "Advanced"},
        {"category": "Edib", "value": 23, "type": "Advanced"},
        {"category": "Funding", "value": 30, "type": "Basic"},
        {"category": "Governance", "value": 22, "type": "Basic"},
        {"category": "Open Science", "value": 14, "type": "Basic"},
        {"category": "Edition", "value": 38, "type": "Basic"},
        {"category": "Technical", "value": 23, "type": "Basic"},
        {"category": "Visibility", "value": 23, "type": "Basic"},
        {"category": "Edib", "value": 23, "type": "Basic"}
      ],
      "transform": [
        {
          "type": "filter",
          "expr": "datum.type === Questions || Questions === 'all'"
        }
      ]
    },
    {
      "name": "keys",
      "source": "table",
      "transform": [
        {
          "type": "aggregate",
          "groupby": ["category"]
        }
      ]
    }
  ],

  "scales": [
    {
      "name": "angular",
      "type": "point",
      "range": {"signal": "[-PI, PI]"},
      "padding": 0.5,
      "domain": {"data": "table", "field": "category"}
    },
    {
      "name": "radial",
      "type": "linear",
      "range": {"signal": "[0, radius]"},
      "zero": true,
      "nice": false,
      // "domain": {"data": "table", "field": "value"},
      "domain": [0, 100],
      "domainMin": 0
    },
    {
      "name": "color",
      "type": "ordinal",
      "domain": ["Advanced", "Basic"],
      "range": ["#1f77b4", "#ff7f0e"]
    }
  ],
  "legends": [
    {
      "title": "Questions type",
      "fill": "color",
      "orient": "none",
      "titleFontSize": 12,
      "labelFontSize": 10,
      "legendX": 350,
      "legendY": -280
    }
  ],


  "marks": [
    {
      "type": "group",
      "name": "categories",
      "zindex": 1,
      "from": {
        "facet": {"data": "table", "name": "facet", "groupby": ["type"]}
      },

      "marks": [
        {
          "type": "line",
          "name": "category-line",
          "from": {"data": "facet"},
          "encode": {
            "enter": {
              "interpolate": {"value": "linear-closed"},
              "x": {"signal": "scale('radial', datum.value) * cos(scale('angular', datum.category))"},
              "y": {"signal": "scale('radial', datum.value) * sin(scale('angular', datum.category))"},
              "stroke": {"scale": "color", "field": "type"},
              "strokeWidth": {"value": 1},
              "fill": {"scale": "color", "field": "type"},
              "fillOpacity": {"value": 0.1}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.value, 'Category': datum.category}"}
            }
          }
        },
        {
          "type": "text",
          "name": "value-text",
          "from": {"data": "category-line"},
          "encode": {
            "enter": {
              "x": {"signal": "datum.x"},
              "y": {"signal": "datum.y"},
              "text": {"signal": "datum.datum.value"},
              "align": {"value": "center"},
              "baseline": {"value": "middle"},
              "fill": {"value": "black"}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.datum.value, 'Category': datum.datum.category}"}
            }
          }
        }
      ]
    },
    {
      "type": "rule",
      "name": "radial-grid",
      "from": {"data": "keys"},
      "zindex": 0,
      "encode": {
        "enter": {
          "x": {"value": 0},
          "y": {"value": 0},
          "x2": {"signal": "radius * cos(scale('angular', datum.category))"},
          "y2": {"signal": "radius * sin(scale('angular', datum.category))"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    },
    {
      "type": "text",
      "name": "key-label",
      "from": {"data": "keys"},
      "zindex": 1,
      "encode": {
        "enter": {
          "x": {"signal": "(radius + 5) * cos(scale('angular', datum.category))"},
          "y": {"signal": "(radius + 5) * sin(scale('angular', datum.category))"},
          "text": {"field": "category"},
          "align": [
            {
              "test": "abs(scale('angular', datum.category)) > PI / 2",
              "value": "right"
            },
            {
              "value": "left"
            }
          ],
          "baseline": [
            {
              "test": "scale('angular', datum.category) > 0", "value": "top"
            },
            {
              "test": "scale('angular', datum.category) == 0", "value": "middle"
            },
            {
              "value": "bottom"
            }
          ],
          "fill": {"value": "black"},
          "fontWeight": {"value": "bold"}
        }
      }
    },
    {
      "type": "line",
      "name": "outer-line",
      "from": {"data": "radial-grid"},
      "encode": {
        "enter": {
          "interpolate": {"value": "linear-closed"},
          "x": {"field": "x2"},
          "y": {"field": "y2"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    }
  ]
}

export const vegaArañaPDF: Spec ={
  "background": "white",
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "title": {
    "text": "",
    "dy": 0,
    "anchor": "start",
    "encode": {"title": {"enter": {"fill": {"value": "#72777a"}}}}
  },
  "width": 200,
  "height": 280,
  "padding": 0,

  "signals": [
    {"name": "radius", "update": "width / 1.5"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "mousemove", "update": "datum"},
        {"events": "mouseout", "update": "{}"}
      ]
    },
    {
      "name": "Preguntas",
      "value": "all",
      "bind" : {
        "input": "select",
        "options": ["all", "Avanzado", "Básico"],
        "labels": ["Todas", "Avanzado", "Básico"]
      }
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Funding", "value": 30, "type": "Avanzado"},
        {"category": "Governance", "value": 22, "type": "Avanzado"},
        {"category": "Open Science", "value": 14, "type": "Avanzado"},
        {"category": "Edition", "value": 38, "type": "Avanzado"},
        {"category": "Technical", "value": 23, "type": "Avanzado"},
        {"category": "Visibility", "value": 23, "type": "Avanzado"},
        {"category": "Edib", "value": 23, "type": "Avanzado"},
        {"category": "Funding", "value": 30, "type": "Básico"},
        {"category": "Governance", "value": 22, "type": "Básico"},
        {"category": "Open Science", "value": 14, "type": "Básico"},
        {"category": "Edition", "value": 38, "type": "Básico"},
        {"category": "Technical", "value": 23, "type": "Básico"},
        {"category": "Visibility", "value": 23, "type": "Básico"},
        {"category": "Edib", "value": 23, "type": "Básico"}
      ],
      "transform": [
        {
          "type": "filter",
          "expr": "datum.type === Preguntas || Preguntas === 'all'"
        }
      ]
    },
    {
      "name": "keys",
      "source": "table",
      "transform": [
        {
          "type": "aggregate",
          "groupby": ["category"]
        }
      ]
    }
  ],

  "scales": [
    {
      "name": "angular",
      "type": "point",
      "range": {"signal": "[-PI, PI]"},
      "padding": 0.5,
      "domain": {"data": "table", "field": "category"}
    },
    {
      "name": "radial",
      "type": "linear",
      "range": {"signal": "[0, radius]"},
      "zero": true,
      "nice": false,
      "domain": [0, 100],
      // "domain": {"data": "table", "field": "value"},
      "domainMin": 0
    },
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "type"},
      "range": {"scheme": "category10"}
    }
  ],
  "legends": [
    {
      "title": "Tipo de Preguntas",
      "fill": "color",
      "orient": "none",
      "titleFontSize": 12,
      "labelFontSize": 10,
      "legendX": 100,
      "legendY": -200
    }
  ],


  "marks": [
    {
      "type": "group",
      "name": "categories",
      "zindex": 1,
      "from": {
        "facet": {"data": "table", "name": "facet", "groupby": ["type"]}
      },

      "marks": [
        {
          "type": "line",
          "name": "category-line",
          "from": {"data": "facet"},
          "encode": {
            "enter": {
              "interpolate": {"value": "linear-closed"},
              "x": {"signal": "scale('radial', datum.value) * cos(scale('angular', datum.category))"},
              "y": {"signal": "scale('radial', datum.value) * sin(scale('angular', datum.category))"},
              "stroke": {"scale": "color", "field": "type"},
              "strokeWidth": {"value": 1},
              "fill": {"scale": "color", "field": "type"},
              "fillOpacity": {"value": 0.1}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.value, 'Category': datum.category}"}
            }
          }
        },
        {
          "type": "text",
          "name": "value-text",
          "from": {"data": "category-line"},
          "encode": {
            "enter": {
              "x": {"signal": "datum.x"},
              "y": {"signal": "datum.y"},
              "text": {"signal": "datum.datum.value"},
              "align": {"value": "center"},
              "baseline": {"value": "middle"},
              "fill": {"value": "black"}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.datum.value, 'Category': datum.datum.category}"}
            }
          }
        }
      ]
    },
    {
      "type": "rule",
      "name": "radial-grid",
      "from": {"data": "keys"},
      "zindex": 0,
      "encode": {
        "enter": {
          "x": {"value": 0},
          "y": {"value": 0},
          "x2": {"signal": "radius * cos(scale('angular', datum.category))"},
          "y2": {"signal": "radius * sin(scale('angular', datum.category))"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    },
    {
      "type": "text",
      "name": "key-label",
      "from": {"data": "keys"},
      "zindex": 1,
      "encode": {
        "enter": {
          "x": {"signal": "(radius + 5) * cos(scale('angular', datum.category))"},
          "y": {"signal": "(radius + 5) * sin(scale('angular', datum.category))"},
          "text": {"field": "category"},
          "align": [
            {
              "test": "abs(scale('angular', datum.category)) > PI / 2",
              "value": "right"
            },
            {
              "value": "left"
            }
          ],
          "baseline": [
            {
              "test": "scale('angular', datum.category) > 0", "value": "top"
            },
            {
              "test": "scale('angular', datum.category) == 0", "value": "middle"
            },
            {
              "value": "bottom"
            }
          ],
          "fill": {"value": "black"},
          "fontWeight": {"value": "bold"}
        }
      }
    },
    {
      "type": "line",
      "name": "outer-line",
      "from": {"data": "radial-grid"},
      "encode": {
        "enter": {
          "interpolate": {"value": "linear-closed"},
          "x": {"field": "x2"},
          "y": {"field": "y2"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    }
  ]
}

export const vegaArañaPDFEN: Spec ={
  "background": "white",
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "title": {
    "text": "",
    "dy": 0,
    "anchor": "start",
    "encode": {"title": {"enter": {"fill": {"value": "#72777a"}}}}
  },
  "width": 200,
  "height": 280,
  "padding": 0,

  "signals": [
    {"name": "radius", "update": "width / 1.5"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "mousemove", "update": "datum"},
        {"events": "mouseout", "update": "{}"}
      ]
    },
    {
      "name": "Questions",
      "value": "all",
      "bind" : {
        "input": "select",
        "options": ["all", "Advanced", "Basic"],
        "labels": ["Todas", "Advanced", "Basic"]
      }
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Funding", "value": 30, "type": "Advanced"},
        {"category": "Governance", "value": 22, "type": "Advanced"},
        {"category": "Open Science", "value": 14, "type": "Advanced"},
        {"category": "Edition", "value": 38, "type": "Advanced"},
        {"category": "Technical", "value": 23, "type": "Advanced"},
        {"category": "Visibility", "value": 23, "type": "Advanced"},
        {"category": "Edib", "value": 23, "type": "Advanced"},
        {"category": "Funding", "value": 30, "type": "Basic"},
        {"category": "Governance", "value": 22, "type": "Basic"},
        {"category": "Open Science", "value": 14, "type": "Basic"},
        {"category": "Edition", "value": 38, "type": "Basic"},
        {"category": "Technical", "value": 23, "type": "Basic"},
        {"category": "Visibility", "value": 23, "type": "Basic"},
        {"category": "Edib", "value": 23, "type": "Basic"}
      ],
      "transform": [
        {
          "type": "filter",
          "expr": "datum.type === Questions || Questions === 'all'"
        }
      ]
    },
    {
      "name": "keys",
      "source": "table",
      "transform": [
        {
          "type": "aggregate",
          "groupby": ["category"]
        }
      ]
    }
  ],

  "scales": [
    {
      "name": "angular",
      "type": "point",
      "range": {"signal": "[-PI, PI]"},
      "padding": 0.5,
      "domain": {"data": "table", "field": "category"}
    },
    {
      "name": "radial",
      "type": "linear",
      "range": {"signal": "[0, radius]"},
      "zero": true,
      "nice": false,
      "domain": [0, 100],
      // "domain": {"data": "table", "field": "value"},
      "domainMin": 0
    },
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "type"},
      "range": {"scheme": "category10"}
    }
  ],
  "legends": [
    {
      "title": "Questions type",
      "fill": "color",
      "orient": "none",
      "titleFontSize": 12,
      "labelFontSize": 10,
      "legendX": 100,
      "legendY": -200
    }
  ],


  "marks": [
    {
      "type": "group",
      "name": "categories",
      "zindex": 1,
      "from": {
        "facet": {"data": "table", "name": "facet", "groupby": ["type"]}
      },

      "marks": [
        {
          "type": "line",
          "name": "category-line",
          "from": {"data": "facet"},
          "encode": {
            "enter": {
              "interpolate": {"value": "linear-closed"},
              "x": {"signal": "scale('radial', datum.value) * cos(scale('angular', datum.category))"},
              "y": {"signal": "scale('radial', datum.value) * sin(scale('angular', datum.category))"},
              "stroke": {"scale": "color", "field": "type"},
              "strokeWidth": {"value": 1},
              "fill": {"scale": "color", "field": "type"},
              "fillOpacity": {"value": 0.1}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.value, 'Category': datum.category}"}
            }
          }
        },
        {
          "type": "text",
          "name": "value-text",
          "from": {"data": "category-line"},
          "encode": {
            "enter": {
              "x": {"signal": "datum.x"},
              "y": {"signal": "datum.y"},
              "text": {"signal": "datum.datum.value"},
              "align": {"value": "center"},
              "baseline": {"value": "middle"},
              "fill": {"value": "black"}
            },
            "update": {
              "tooltip": {"signal": "{'Value': datum.datum.value, 'Category': datum.datum.category}"}
            }
          }
        }
      ]
    },
    {
      "type": "rule",
      "name": "radial-grid",
      "from": {"data": "keys"},
      "zindex": 0,
      "encode": {
        "enter": {
          "x": {"value": 0},
          "y": {"value": 0},
          "x2": {"signal": "radius * cos(scale('angular', datum.category))"},
          "y2": {"signal": "radius * sin(scale('angular', datum.category))"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    },
    {
      "type": "text",
      "name": "key-label",
      "from": {"data": "keys"},
      "zindex": 1,
      "encode": {
        "enter": {
          "x": {"signal": "(radius + 5) * cos(scale('angular', datum.category))"},
          "y": {"signal": "(radius + 5) * sin(scale('angular', datum.category))"},
          "text": {"field": "category"},
          "align": [
            {
              "test": "abs(scale('angular', datum.category)) > PI / 2",
              "value": "right"
            },
            {
              "value": "left"
            }
          ],
          "baseline": [
            {
              "test": "scale('angular', datum.category) > 0", "value": "top"
            },
            {
              "test": "scale('angular', datum.category) == 0", "value": "middle"
            },
            {
              "value": "bottom"
            }
          ],
          "fill": {"value": "black"},
          "fontWeight": {"value": "bold"}
        }
      }
    },
    {
      "type": "line",
      "name": "outer-line",
      "from": {"data": "radial-grid"},
      "encode": {
        "enter": {
          "interpolate": {"value": "linear-closed"},
          "x": {"field": "x2"},
          "y": {"field": "y2"},
          "stroke": {"value": "lightgray"},
          "strokeWidth": {"value": 1}
        }
      }
    }
  ]
}

export const vegaBarras: Spec ={
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "title": {
    "text": "",
    "dy": -30,
    "anchor": "start",
    "encode": {"title": {"enter": {"fill": {"value": "#72777a"}}}}
  },
  "width": 800,
  "height": 200,
  "padding": 5,

  "data": [
    {
      "name": "table",
      "values": [
        {"key": "Funding", "value": 30, "category": "Advanced"},
        {"key": "Governance", "value": 22, "category": "Advanced"},
        {"key": "Open Science", "value": 14, "category": "Advanced"},
        {"key": "Edition", "value": 38, "category": "Advanced"},
        {"key": "Technical", "value": 23, "category": "Advanced"},
        {"key": "Visibility", "value": 23, "category": "Advanced"},
        {"key": "Edib", "value": 23, "category": "Advanced"},
        {"key": "Funding", "value": 30, "category": "Basic"},
        {"key": "Governance", "value": 22, "category": "Basic"},
        {"key": "Open Science", "value": 14, "category": "Basic"},
        {"key": "Edition", "value": 38, "category": "Basic"},
        {"key": "Technical", "value": 23, "category": "Basic"},
        {"key": "Visibility", "value": 23, "category": "Basic"},
        {"key": "Edib", "value": 23, "category": "Basic"}
      ]
    }
  ],

  "scales": [
    {
      "name": "xscale",
      "type": "band",
      "domain": {"data": "table", "field": "key"},
      "range": "width",
      "padding": 0.2
    },
    {
      "name": "yscale",
      "type": "linear",
      // "domain": {"data": "table", "field": "value"},
      "domain": [0, 100],
      "nice": true,
      "range": "height"
    },
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "category"},
      "range": {"scheme": "category10"}
    }
  ],
  "legends": [
    {
      "symbolType": "square",
      "fill": "color",
      "encode": {
        "title": {"update": {"fontSize": {"value": 14}}},
        "labels": {
          "interactive": true,
          "update": {"fontSize": {"value": 12}, "fill": {"value": "black"}},
          "hover": {"fill": {"value": "firebrick"}}
        },
        "symbols": {"update": {"stroke": {"value": "transparent"}}}
      }
    }
  ],

  "axes": [
    {"orient": "bottom", "scale": "xscale"},
    {"orient": "left", "scale": "yscale"}
  ],

  "marks": [
    {
      "type": "group",
      "from": {
        "facet": {
          "name": "facet",
          "data": "table",
          "groupby": "key",
          "aggregate": {
            "fields": ["value", "value"],
            "ops": ["sum", "count"],
            "as": ["value", "count"]
          }
        }
      },
      "encode": {
        "enter": {
          "x": {"scale": "xscale", "field": "key"}
        }
      },
      "signals": [
        {"name": "width", "update": "bandwidth('xscale')"}
      ],
      "scales": [
        {
          "name": "pos",
          "type": "band",
          "range": "width",
          "domain": {"data": "facet", "field": "category"}
        }
      ],
      "marks": [
        {
          "type": "rect",
          "from": {"data": "facet"},
          "encode": {
            "enter": {
              "x": {"scale": "pos", "field": "category"},
              "width": {"scale": "pos", "band": 1},
              "y": {"scale": "yscale", "field": "value"},
              "y2": {"scale": "yscale", "value": 0},
              "fill": {"scale": "color", "field": "category"}
            },
            "update": {
              "fillOpacity": {"value": 1},
              "tooltip": {
                "signal": "{'Value': datum.value + ' %'}"
              }
            },
            "hover": {
              "fillOpacity": {"value": 0.5}
            }
          }
        }
      ]
    }
  ]
}

export const vegaDonut: Spec =  {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "width": 300,
  "height": 150,
  "padding": 5,
  "signals": [
    {"name": "radius", "update": "width / 2"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "arc:mouseover", "update": "datum"},
        {"events": "arc:mouseout", "update": "{}"}
      ]
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Funding", "value": 50}
      ],
      "transform": [
        {
          "type": "formula",
          "expr": "-1.57",
          "as": "startAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value / 100 * 3.14 - 1.57",
          "as": "endAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'",
          "as": "color"
        }
      ]
    },
    {
      "name": "background",
      "values": [{}],
      "transform": [
        {"type": "formula", "expr": "-1.57", "as": "startAngle"},
        {"type": "formula", "expr": "1.57", "as": "endAngle"}
      ]
    }
  ],

  "scales": [
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "category"},
      "range": {"signal": "data('table')[0].color"}
    }
  ],

  "marks": [
    {
      "type": "arc",
      "from": {"data": "background"},
      "encode": {
        "enter": {
          "fill": {"value": "lightgrey"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        }
      }
    },
    {
      "type": "arc",
      "from": {"data": "table"},
      "encode": {
        "enter": {
          "fill": {
            "signal": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'"
          },
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        },
        "update": {
          "opacity": {"value": 1},
          "tooltip": {"signal": "{'Category': datum.category, 'Value': datum.value + ' %'}"}
        },
        "hover": {
          "opacity": {"value": 0.5}
        }
      }
    },
    {
      "type": "text",
      "encode": {
        "enter": {
          "align": {"value": "center"},
          "baseline": {"value": "middle"},
          "fill": {"value": "black"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 100"},
          "fontSize": {"value": 25},
          "text": {
            "signal": "data('table')[0].value +' %'"
          }
        }
      }
    }
  ],

  "legends": [
    {
      "symbolType": "square",
      "fill": "color",
      "orient": "none",
      "legendY": -15,
      "titleFontSize": 12,
      "labelFontSize": 20,
      "encode": {
        "title": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        },
        "symbols": {
          "update": {
            "fill": {
              "signal": "data('table')[0].color"
            },
            "align": {"value": "center"},
            "x": {"signal": "width / 3.5"}
          }
        },
        "labels": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        }
      }
    }
  ]
};
export const vegaDonutInteroperabilidad: Spec =  {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "width": 300,
  "height": 150,
  "padding": 5,
  "signals": [
    {"name": "radius", "update": "width / 2"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "arc:mouseover", "update": "datum"},
        {"events": "arc:mouseout", "update": "{}"}
      ]
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Metadatos", "value": 50}
      ],
      "transform": [
        {
          "type": "formula",
          "expr": "-1.57",
          "as": "startAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value / 100 * 3.14 - 1.57",
          "as": "endAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'",
          "as": "color"
        }
      ]
    },
    {
      "name": "background",
      "values": [{}],
      "transform": [
        {"type": "formula", "expr": "-1.57", "as": "startAngle"},
        {"type": "formula", "expr": "1.57", "as": "endAngle"}
      ]
    }
  ],

  "scales": [
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "category"},
      "range": {"signal": "data('table')[0].color"}
    }
  ],

  "marks": [
    {
      "type": "arc",
      "from": {"data": "background"},
      "encode": {
        "enter": {
          "fill": {"value": "lightgrey"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        }
      }
    },
    {
      "type": "arc",
      "from": {"data": "table"},
      "encode": {
        "enter": {
          "fill": {
            "signal": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'"
          },
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        },
        "update": {
          "opacity": {"value": 1},
          "tooltip": {"signal": "{'Category': datum.category, 'Value': datum.value + ' %'}"}
        },
        "hover": {
          "opacity": {"value": 0.5}
        }
      }
    },
    {
      "type": "text",
      "encode": {
        "enter": {
          "align": {"value": "center"},
          "baseline": {"value": "middle"},
          "fill": {"value": "black"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 100"},
          "fontSize": {"value": 25},
          "text": {
            "signal": "data('table')[0].value +' %'"
          }
        }
      }
    }
  ],

  "legends": [
    {
      "symbolType": "square",
      "fill": "color",
      "orient": "none",
      "legendY": -15,
      "titleFontSize": 12,
      "labelFontSize": 20,
      "encode": {
        "title": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        },
        "symbols": {
          "update": {
            "fill": {
              "signal": "data('table')[0].color"
            },
            "align": {"value": "center"},
            "x": {"signal": "width / 4.8"}
          }
        },
        "labels": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        }
      }
    }
  ]
};

export const vegaDonutGeneral: Spec =  {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "width": 300,
  "height": 150,
  "padding": 5,
  "signals": [
    {"name": "radius", "update": "width / 2"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "arc:mouseover", "update": "datum"},
        {"events": "arc:mouseout", "update": "{}"}
      ]
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Metadatos", "value": 50}
      ],
      "transform": [
        {
          "type": "formula",
          "expr": "-1.57",
          "as": "startAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value / 100 * 3.14 - 1.57",
          "as": "endAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'",
          "as": "color"
        }
      ]
    },
    {
      "name": "background",
      "values": [{}],
      "transform": [
        {"type": "formula", "expr": "-1.57", "as": "startAngle"},
        {"type": "formula", "expr": "1.57", "as": "endAngle"}
      ]
    }
  ],

  "scales": [
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "category"},
      "range": {"signal": "data('table')[0].color"}
    }
  ],

  "marks": [
    {
      "type": "arc",
      "from": {"data": "background"},
      "encode": {
        "enter": {
          "fill": {"value": "lightgrey"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        }
      }
    },
    {
      "type": "arc",
      "from": {"data": "table"},
      "encode": {
        "enter": {
          "fill": {
            "signal": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'"
          },
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        },
        "update": {
          "opacity": {"value": 1},
          "tooltip": {"signal": "{'Category': datum.category, 'Value': datum.value + ' %'}"}
        },
        "hover": {
          "opacity": {"value": 0.5}
        }
      }
    },
    {
      "type": "text",
      "encode": {
        "enter": {
          "align": {"value": "center"},
          "baseline": {"value": "middle"},
          "fill": {"value": "black"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 100"},
          "fontSize": {"value": 25},
          "text": {
            "signal": "data('table')[0].value +' %'"
          }
        }
      }
    }
  ],

  "legends": [
    {
      "symbolType": "square",
      "fill": "color",
      "orient": "none",
      "legendY": -15,
      "titleFontSize": 12,
      "labelFontSize": 20,
      "encode": {
        "title": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        },
        "symbols": {
          "update": {
            "fill": {
              "signal": "data('table')[0].color"
            },
            "align": {"value": "center"},
            "x": {"signal": "width / 4.8"}
          }
        },
        "labels": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        }
      }
    }
  ]
};

export const vegaDonutLogs: Spec =  {
  "$schema": "https://vega.github.io/schema/vega/v5.json",
  "width": 300,
  "height": 150,
  "padding": 5,
  "signals": [
    {"name": "radius", "update": "width / 2"},
    {
      "name": "tooltip",
      "value": {},
      "on": [
        {"events": "arc:mouseover", "update": "datum"},
        {"events": "arc:mouseout", "update": "{}"}
      ]
    }
  ],

  "data": [
    {
      "name": "table",
      "values": [
        {"category": "Metadatos", "value": 50}
      ],
      "transform": [
        {
          "type": "formula",
          "expr": "-1.57",
          "as": "startAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value / 100 * 3.14 - 1.57",
          "as": "endAngle"
        },
        {
          "type": "formula",
          "expr": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'",
          "as": "color"
        }
      ]
    },
    {
      "name": "background",
      "values": [{}],
      "transform": [
        {"type": "formula", "expr": "-1.57", "as": "startAngle"},
        {"type": "formula", "expr": "1.57", "as": "endAngle"}
      ]
    }
  ],

  "scales": [
    {
      "name": "color",
      "type": "ordinal",
      "domain": {"data": "table", "field": "category"},
      "range": {"signal": "data('table')[0].color"}
    }
  ],

  "marks": [
    {
      "type": "arc",
      "from": {"data": "background"},
      "encode": {
        "enter": {
          "fill": {"value": "lightgrey"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        }
      }
    },
    {
      "type": "arc",
      "from": {"data": "table"},
      "encode": {
        "enter": {
          "fill": {
            "signal": "datum.value >= 75 ? '#24a35a' : datum.value >= 40 ? '#efcf2e' : '#e74c3c'"
          },
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 50"},
          "startAngle": {"field": "startAngle"},
          "endAngle": {"field": "endAngle"},
          "innerRadius": {"value": 30},
          "outerRadius": {"value": 80}
        },
        "update": {
          "opacity": {"value": 1},
          "tooltip": {"signal": "{'Category': datum.category, 'Value': datum.value + ' %'}"}
        },
        "hover": {
          "opacity": {"value": 0.5}
        }
      }
    },
    {
      "type": "text",
      "encode": {
        "enter": {
          "align": {"value": "center"},
          "baseline": {"value": "middle"},
          "fill": {"value": "black"},
          "x": {"signal": "width / 2"},
          "y": {"signal": "height / 2 + 100"},
          "fontSize": {"value": 25},
          "text": {
            "signal": "data('table')[0].value +' %'"
          }
        }
      }
    }
  ],

  "legends": [
    {
      "symbolType": "square",
      "fill": "color",
      "orient": "none",
      "legendY": -15,
      "titleFontSize": 12,
      "labelFontSize": 20,
      "encode": {
        "title": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 1"}
          }
        },
        "symbols": {
          "update": {
            "fill": {
              "signal": "data('table')[0].color"
            },
            "align": {"value": "center"},
            "x": {"signal": "width / 5.0"}
          }
        },
        "labels": {
          "update": {
            "align": {"value": "center"},
            "x": {"signal": "width / 2"}
          }
        }
      }
    }
  ]
};

