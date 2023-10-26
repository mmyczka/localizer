-- Insert data into the items table to create the hierarchy
USE [localizer]

INSERT INTO [dbo].[items] ([id], [name], [parent_id]) VALUES
    (1, 'Garage', NULL),  -- Root location
    (2, 'Living Room', NULL),  -- Root location
    (3, 'Workbench', 1),  -- Child of Garage
    (4, 'Wardrobe', 2),  -- Child of Living Room
    (5, 'Second Drawer', 3),  -- Child of Table
    (6, 'Second Shelf', 4),  -- Child of Wardrobe
    (7, 'Black Box', 5),  -- Child of Second Drawer
	(8, 'Hammer', 7), -- Child of Black Box
	(9, 'Phone', 6); -- Child fo Second Shelf
