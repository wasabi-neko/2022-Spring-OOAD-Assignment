package mod.instance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;

import Define.AreaDefine;
import Pack.DragPack;
import bgWork.handler.CanvasPanelHandler;
import mod.IFuncComponent;
import mod.ILinePainter;
import java.lang.Math;

public class GeneralizationLine extends ConnectionLine
{
	int					arrowSize		= 6;
	int					panelExtendSize	= 10;

	public GeneralizationLine(CanvasPanelHandler cph)
	{
        super(cph);
	}

    public JPanel getFromPortPanel() {
        JPanel portPanel = new JPanel();
        portPanel.setSize(20, 20);
        portPanel.setLocation(fp.x - 10, fp.y - 10);
        return portPanel;
    }

    public JPanel getToPortPanel() {
        JPanel portPanel = new JPanel();
        portPanel.setSize(20, 20);
        portPanel.setLocation(tp.x - 10, fp.y - 10);
        return portPanel;
    }

	@Override
	public void paintComponent(Graphics g)
	{
		Point fpPrime;
		Point tpPrime;
		renewConnect();
		fpPrime = new Point(fp.x - this.getLocation().x,
				fp.y - this.getLocation().y);
		tpPrime = new Point(tp.x - this.getLocation().x,
				tp.y - this.getLocation().y);

		if (isSelect == true)
		{
			paintSelect(g);
		}

		g.setColor(Color.BLACK);
		g.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
		paintArrow(g, tpPrime);
	}

	@Override
	public void reSize()
	{
		Dimension size = new Dimension(
				Math.abs(fp.x - tp.x) + panelExtendSize * 2,
				Math.abs(fp.y - tp.y) + panelExtendSize * 2);
		this.setSize(size);
		this.setLocation(Math.min(fp.x, tp.x) - panelExtendSize,
				Math.min(fp.y, tp.y) - panelExtendSize);
	}

	@Override
	public void paintArrow(Graphics g, Point point)
	{
		int x[] =
		{point.x, point.x - arrowSize, point.x, point.x + arrowSize};
		int y[] =
		{point.y + arrowSize, point.y, point.y - arrowSize, point.y};
		switch (toSide)
		{
			case 0:
				x = removeAt(x, 0);
				y = removeAt(y, 0);
				break;
			case 1:
				x = removeAt(x, 1);
				y = removeAt(y, 1);
				break;
			case 2:
				x = removeAt(x, 3);
				y = removeAt(y, 3);
				break;
			case 3:
				x = removeAt(x, 2);
				y = removeAt(y, 2);
				break;
			default:
				break;
		}
		Polygon polygon = new Polygon(x, y, x.length);
		g.setColor(Color.WHITE);
		g.fillPolygon(polygon);
		g.setColor(Color.BLACK);
		g.drawPolygon(polygon);
	}

	@Override
	public void setConnect(DragPack dPack)
	{
		Point mfp = dPack.getFrom();
		Point mtp = dPack.getTo();
		from = (JPanel) dPack.getFromObj();
		to = (JPanel) dPack.getToObj();
		fromSide = new AreaDefine().getArea(from.getLocation(), from.getSize(),
				mfp);
		toSide = new AreaDefine().getArea(to.getLocation(), to.getSize(), mtp);
		renewConnect();
		System.out.println("from side " + fromSide);
		System.out.println("to side " + toSide);
		;
	}

	int[] removeAt(int arr[], int index)
	{
		int temp[] = new int[arr.length - 1];
		for (int i = 0; i < temp.length; i ++)
		{
			if (i < index)
			{
				temp[i] = arr[i];
			}
			else if (i >= index)
			{
				temp[i] = arr[i + 1];
			}
		}
		return temp;
	}

	@Override
	public void paintSelect(Graphics g)
	{
		Point fpPrime;
		Point tpPrime;
		fpPrime = new Point(fp.x - this.getLocation().x,
				fp.y - this.getLocation().y);
		tpPrime = new Point(tp.x - this.getLocation().x,
				tp.y - this.getLocation().y);

        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(5);
        g2d.setStroke(dashed);
        g2d.setColor(Color.CYAN);
        g2d.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
	}
}
